package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.furbaby.furbaby.dto.OrderCreateDTO;
import com.furbaby.furbaby.entity.Order;
import com.furbaby.furbaby.entity.Pet;
import com.furbaby.furbaby.entity.Shop;
import com.furbaby.furbaby.entity.ShopSchedule;
import com.furbaby.furbaby.enums.OrderStatus;
import com.furbaby.furbaby.enums.ShopStatus;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.OrderMapper;
import com.furbaby.furbaby.mapper.PetMapper;
import com.furbaby.furbaby.mapper.ShopMapper;
import com.furbaby.furbaby.mapper.ShopScheduleMapper;
import com.furbaby.furbaby.service.IOrderService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.OrderCreateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public OrderCreateVO createOrder(String token, OrderCreateDTO dto) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Shop shop = shopMapper.selectOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, dto.getShopId()));
        if (shop == null) {
            throw new NoRegisterException("商家不存在");
        }
        if (shop.getStatus() != ShopStatus.approved) {
            throw new NoRegisterException("商家暂未通过审核");
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

        return OrderCreateVO.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .status(order.getStatus().name())
                .amount(order.getAmount())
                .createTime(order.getCreateTime())
                .build();
    }
}
