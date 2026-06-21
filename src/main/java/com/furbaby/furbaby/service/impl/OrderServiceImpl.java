package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.furbaby.furbaby.cache.CacheHelper;
import com.furbaby.furbaby.lock.DistributedLock;
import com.furbaby.furbaby.dto.OrderCancelDTO;
import com.furbaby.furbaby.dto.OrderCreateDTO;
import com.furbaby.furbaby.entity.Order;
import com.furbaby.furbaby.entity.Pet;
import com.furbaby.furbaby.entity.Shop;
import com.furbaby.furbaby.entity.ShopSchedule;
import com.furbaby.furbaby.enums.OrderStatus;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.OrderMapper;
import com.furbaby.furbaby.mapper.PetMapper;
import com.furbaby.furbaby.mapper.ShopMapper;
import com.furbaby.furbaby.mapper.ShopScheduleMapper;
import com.furbaby.furbaby.service.IOrderService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.OrderCreateVO;
import com.furbaby.furbaby.vo.OrderDetailVO;
import com.furbaby.furbaby.vo.OrderItemVO;
import com.furbaby.furbaby.vo.OrderStatusVO;
import com.furbaby.furbaby.vo.PageResult;
import com.furbaby.furbaby.vo.StatusStepVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final ShopScheduleMapper shopScheduleMapper;
    private final ShopMapper shopMapper;
    private final PetMapper petMapper;
    private final JWTUtils jwtUtils;
    private final CacheHelper cacheHelper;
    private final DistributedLock distributedLock;

    @Override
    public OrderCreateVO createOrder(String token, OrderCreateDTO dto) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Shop shop = shopMapper.selectOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, dto.getShopId()));
        if (shop == null) {
            throw new NoRegisterException("商家不存在");
        }
        if (!"open".equals(shop.getBizStatus())) {
            throw new NoRegisterException("商家已休息，暂无法预约");
        }

        Pet pet = petMapper.selectOne(Wrappers.<Pet>lambdaQuery().eq(Pet::getId, dto.getPetId()));
        if (pet == null) {
            throw new NoRegisterException("宠物不存在");
        }
        if (!pet.getOwnerId().equals(userId)) {
            throw new NoRegisterException("该宠物不属于当前用户");
        }

        LocalDate today = LocalDate.now();
        if (dto.getStartDate().isBefore(today)) {
            throw new NoRegisterException("入住日期不能早于今天");
        }
        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            throw new NoRegisterException("离店日期必须晚于入住日期");
        }

        // 分布式锁：防止同一商家档期并发超卖
        String lockHolder = distributedLock.tryLock(
                "shop:schedule:" + dto.getShopId(),
                Duration.ofSeconds(10),
                Duration.ofSeconds(5));
        if (lockHolder == null) {
            throw new NoRegisterException("当前预约人数较多，请稍后重试");
        }

        try {
            List<ShopSchedule> schedules = shopScheduleMapper.selectList(
                    Wrappers.<ShopSchedule>lambdaQuery()
                            .eq(ShopSchedule::getShopId, dto.getShopId())
                            .ge(ShopSchedule::getDate, dto.getStartDate())
                            .lt(ShopSchedule::getDate, dto.getEndDate()));

            Map<LocalDate, ShopSchedule> scheduleMap = schedules.stream()
                    .collect(Collectors.toMap(ShopSchedule::getDate, s -> s));

            BigDecimal amount = BigDecimal.ZERO;
            for (LocalDate date = dto.getStartDate(); date.isBefore(dto.getEndDate()); date = date.plusDays(1)) {
                ShopSchedule s = scheduleMap.get(date);
                if (s == null || s.getAvailable() <= 0) {
                    throw new NoRegisterException(date + " 暂无可用档期");
                }
                amount = amount.add(s.getPrice());
            }

            String orderNo = "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                    + String.format("%04d", new Random().nextInt(10000));

            Order order = Order.builder()
                    .orderNo(orderNo)
                    .userId(userId)
                    .shopId(dto.getShopId())
                    .petId(dto.getPetId())
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .status(OrderStatus.pending)
                    .amount(amount)
                    .remark(dto.getRemark())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            this.save(order);

            for (LocalDate date = dto.getStartDate(); date.isBefore(dto.getEndDate()); date = date.plusDays(1)) {
                ShopSchedule s = scheduleMap.get(date);
                s.setAvailable(s.getAvailable() - 1);
                s.setUpdateTime(LocalDateTime.now());
                shopScheduleMapper.updateById(s);
            }

            cacheHelper.evictPattern("shop:schedule:" + dto.getShopId() + ":*");
            cacheHelper.evictPattern("shop:list:*");

            return OrderCreateVO.builder()
                    .orderId(order.getId())
                    .orderNo(order.getOrderNo())
                    .status(order.getStatus().name())
                    .amount(order.getAmount())
                    .createTime(order.getCreateTime())
                    .build();
        } finally {
            distributedLock.unlock("shop:schedule:" + dto.getShopId(), lockHolder);
        }
    }

    @Override
    public PageResult<OrderItemVO> listOrders(String token, String status, Integer page, Integer size) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery()
                .eq(Order::getUserId, userId);
        if (status != null && !status.isBlank()) {
            wrapper.eq(Order::getStatus, OrderStatus.valueOf(status));
        }
        wrapper.orderByDesc(Order::getCreateTime);

        long total = this.count(wrapper);
        long pages = (total + size - 1) / size;
        int offset = (page - 1) * size;
        wrapper.last("LIMIT " + offset + "," + size);

        List<Order> orders = this.list(wrapper);

        Map<Long, String> shopNameCache = new HashMap<>();
        Map<Long, String> petNameCache = new HashMap<>();

        List<OrderItemVO> records = orders.stream().map(o -> {
            String shopName = shopNameCache.computeIfAbsent(o.getShopId(), shopId -> {
                Shop shop = shopMapper.selectById(shopId);
                return shop != null ? shop.getName() : "未知商家";
            });
            String petName = petNameCache.computeIfAbsent(o.getPetId(), petId -> {
                Pet pet = petMapper.selectById(petId);
                return pet != null ? pet.getName() : "未知宠物";
            });
            return OrderItemVO.builder()
                    .orderId(o.getId())
                    .orderNo(o.getOrderNo())
                    .shopName(shopName)
                    .petName(petName)
                    .startDate(o.getStartDate())
                    .endDate(o.getEndDate())
                    .status(o.getStatus().name())
                    .amount(o.getAmount())
                    .createTime(o.getCreateTime())
                    .build();
        }).collect(Collectors.toList());

        return PageResult.<OrderItemVO>builder()
                .total(total)
                .pages(pages)
                .records(records)
                .build();
    }

    @Override
    public OrderDetailVO getOrderDetail(Long id) {
        Order order = this.getOne(Wrappers.<Order>lambdaQuery().eq(Order::getId, id));
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }

        Shop shop = shopMapper.selectById(order.getShopId());
        Pet pet = petMapper.selectById(order.getPetId());

        return OrderDetailVO.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .shopName(shop != null ? shop.getName() : "未知商家")
                .petName(pet != null ? pet.getName() : "未知宠物")
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .status(order.getStatus().name())
                .amount(order.getAmount())
                .createTime(order.getCreateTime())
                .shopId(order.getShopId())
                .petId(order.getPetId())
                .remark(order.getRemark())
                .payTime(order.getPayTime())
                .cancelTime(order.getCancelTime())
                .build();
    }

    @Override
    public Map<String, String> cancelOrder(String token, Long id, OrderCancelDTO cancelDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Order order = this.getOne(Wrappers.<Order>lambdaQuery().eq(Order::getId, id));
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new NoRegisterException("无权操作他人订单");
        }

        OrderStatus status = order.getStatus();
        if (status != OrderStatus.pending && status != OrderStatus.paid) {
            throw new NoRegisterException("当前订单状态不可取消");
        }

        order.setStatus(OrderStatus.cancelled);
        order.setCancelTime(LocalDateTime.now());
        order.setCancelReason(cancelDTO.getReason());
        order.setUpdateTime(LocalDateTime.now());
        this.updateById(order);

        List<ShopSchedule> schedules = shopScheduleMapper.selectList(
                Wrappers.<ShopSchedule>lambdaQuery()
                        .eq(ShopSchedule::getShopId, order.getShopId())
                        .ge(ShopSchedule::getDate, order.getStartDate())
                        .lt(ShopSchedule::getDate, order.getEndDate()));
        for (ShopSchedule s : schedules) {
            s.setAvailable(s.getAvailable() + 1);
            s.setUpdateTime(LocalDateTime.now());
            shopScheduleMapper.updateById(s);
        }

        cacheHelper.evictPattern("shop:schedule:" + order.getShopId() + ":*");

        return Map.of("success", "true", "status", "cancelled");
    }

    @Override
    public OrderStatusVO getOrderStatus(Long id) {
        Order order = this.getOne(Wrappers.<Order>lambdaQuery().eq(Order::getId, id));
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }

        List<StatusStepVO> timeline = new ArrayList<>();
        timeline.add(StatusStepVO.builder().status("pending").time(order.getCreateTime()).build());

        if (order.getPayTime() != null) {
            timeline.add(StatusStepVO.builder().status("paid").time(order.getPayTime()).build());
        }

        OrderStatus currentStatus = order.getStatus();
        if (currentStatus == OrderStatus.boarding || currentStatus == OrderStatus.completed
                || currentStatus == OrderStatus.refunding || currentStatus == OrderStatus.refunded) {
            timeline.add(StatusStepVO.builder().status("boarding").time(order.getStartDate().atStartOfDay()).build());
        }

        if (currentStatus == OrderStatus.completed) {
            timeline.add(StatusStepVO.builder().status("completed").time(order.getUpdateTime()).build());
        }

        if (currentStatus == OrderStatus.cancelled && order.getCancelTime() != null) {
            timeline.add(StatusStepVO.builder().status("cancelled").time(order.getCancelTime()).build());
        }

        if (currentStatus == OrderStatus.refunding) {
            timeline.add(StatusStepVO.builder().status("refunding").time(order.getUpdateTime()).build());
        }

        if (currentStatus == OrderStatus.refunded) {
            timeline.add(StatusStepVO.builder().status("refunded").time(order.getUpdateTime()).build());
        }

        return OrderStatusVO.builder()
                .orderId(order.getId())
                .status(currentStatus.name())
                .timeline(timeline)
                .build();
    }
}
