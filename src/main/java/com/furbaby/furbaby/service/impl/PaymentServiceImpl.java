package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.furbaby.furbaby.cache.CacheHelper;
import com.furbaby.furbaby.dto.PaymentCreateDTO;
import com.furbaby.furbaby.dto.RefundDTO;
import com.furbaby.furbaby.entity.Order;
import com.furbaby.furbaby.entity.Payment;
import com.furbaby.furbaby.entity.Refund;
import com.furbaby.furbaby.entity.Shop;
import com.furbaby.furbaby.entity.ShopSchedule;
import com.furbaby.furbaby.enums.OrderStatus;
import com.furbaby.furbaby.enums.PaymentStatus;
import com.furbaby.furbaby.enums.RefundStatus;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.OrderMapper;
import com.furbaby.furbaby.mapper.PaymentMapper;
import com.furbaby.furbaby.mapper.RefundMapper;
import com.furbaby.furbaby.mapper.ShopMapper;
import com.furbaby.furbaby.mapper.ShopScheduleMapper;
import com.furbaby.furbaby.service.IPaymentService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.PaymentCreateVO;
import com.furbaby.furbaby.vo.PaymentStatusVO;
import com.furbaby.furbaby.vo.RefundVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;
    private final RefundMapper refundMapper;
    private final ShopMapper shopMapper;
    private final ShopScheduleMapper shopScheduleMapper;
    private final JWTUtils jwtUtils;
    private final CacheHelper cacheHelper;

    @Override
    public PaymentCreateVO createPayment(String token, PaymentCreateDTO createDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Order order = orderMapper.selectOne(Wrappers.<Order>lambdaQuery().eq(Order::getId, createDTO.getOrderId()));
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new NoRegisterException("无权操作他人订单");
        }
        if (order.getStatus() != OrderStatus.pending) {
            throw new NoRegisterException("当前订单状态不可支付");
        }

        String payNo = "PAY" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%04d", new Random().nextInt(10000));

        Payment payment = Payment.builder()
                .paymentNo(payNo)
                .orderId(order.getId())
                .userId(userId)
                .payMethod(createDTO.getPayMethod())
                .amount(order.getAmount())
                .status(PaymentStatus.success)
                .payTime(LocalDateTime.now())
                .expireTime(LocalDateTime.now().plusMinutes(30))
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        if ("wechat".equals(createDTO.getPayMethod().name())) {
            payment.setQrCode("wechat://pay?order=" + payNo);
        } else {
            payment.setPayUrl("https://alipay.example.com/pay?order=" + payNo);
        }

        paymentMapper.insert(payment);

        order.setStatus(OrderStatus.paid);
        order.setPayTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        return PaymentCreateVO.builder()
                .paymentId(payment.getId())
                .payNo(payment.getPaymentNo())
                .amount(payment.getAmount())
                .qrCode(payment.getQrCode())
                .payUrl(payment.getPayUrl())
                .expireTime(payment.getExpireTime())
                .build();
    }

    @Override
    public PaymentStatusVO getPaymentStatus(Long paymentId) {
        Payment payment = paymentMapper.selectOne(Wrappers.<Payment>lambdaQuery().eq(Payment::getId, paymentId));
        if (payment == null) {
            throw new NoRegisterException("支付单不存在");
        }
        return PaymentStatusVO.builder()
                .paymentId(payment.getId())
                .status(payment.getStatus().name())
                .payTime(payment.getPayTime())
                .build();
    }

    @Override
    public RefundVO refund(String token, RefundDTO refundDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Order order = orderMapper.selectOne(Wrappers.<Order>lambdaQuery().eq(Order::getId, refundDTO.getOrderId()));
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }

        // 校验权限：订单主人 或 订单所属店铺的商家
        boolean isOwner = order.getUserId().equals(userId);
        boolean isShopOwner = false;
        Shop shop = shopMapper.selectOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, order.getShopId()));
        if (shop != null && shop.getUserId().equals(userId)) {
            isShopOwner = true;
        }
        if (!isOwner && !isShopOwner) {
            throw new NoRegisterException("无权操作此订单");
        }

        OrderStatus orderStatus = order.getStatus();
        if (orderStatus != OrderStatus.paid && orderStatus != OrderStatus.boarding) {
            throw new NoRegisterException("当前订单状态不可退款");
        }

        Payment payment = paymentMapper.selectOne(Wrappers.<Payment>lambdaQuery()
                .eq(Payment::getOrderId, refundDTO.getOrderId()));
        if (payment == null) {
            throw new NoRegisterException("未找到支付记录");
        }

        Refund refund = Refund.builder()
                .orderId(order.getId())
                .paymentId(payment.getId())
                .userId(userId)
                .amount(order.getAmount())
                .reason(refundDTO.getReason())
                .status(RefundStatus.pending)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        refundMapper.insert(refund);

        order.setStatus(OrderStatus.refunding);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        return RefundVO.builder()
                .refundId(refund.getId())
                .status(refund.getStatus().name())
                .amount(refund.getAmount())
                .build();
    }

    @Override
    public Map<String, String> processRefund(String token, Long refundId, String action) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Refund refund = refundMapper.selectById(refundId);
        if (refund == null) {
            throw new NoRegisterException("退款单不存在");
        }
        if (refund.getStatus() != RefundStatus.pending) {
            throw new NoRegisterException("退款单已处理，不可重复操作");
        }

        Order order = orderMapper.selectById(refund.getOrderId());
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }

        Shop shop = shopMapper.selectOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, order.getShopId()));
        if (shop == null || !shop.getUserId().equals(userId)) {
            throw new NoRegisterException("无权操作此退款，仅该订单所属商家可处理");
        }

        if ("approve".equals(action)) {
            refund.setStatus(RefundStatus.success);
            refund.setUpdateTime(LocalDateTime.now());
            refundMapper.updateById(refund);

            order.setStatus(OrderStatus.refunded);
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);

            // 恢复档期库存
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
            return Map.of("success", "true", "status", "refunded");
        } else if ("reject".equals(action)) {
            refund.setStatus(RefundStatus.failed);
            refund.setUpdateTime(LocalDateTime.now());
            refundMapper.updateById(refund);

            order.setStatus(OrderStatus.boarding);
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
            return Map.of("success", "true", "status", "boarding");
        } else {
            throw new NoRegisterException("操作类型仅支持 approve 或 reject");
        }
    }
}
