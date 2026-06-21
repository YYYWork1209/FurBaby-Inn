package com.furbaby.furbaby.scheduler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.furbaby.furbaby.entity.Order;
import com.furbaby.furbaby.enums.OrderStatus;
import com.furbaby.furbaby.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusScheduler {

    private final OrderMapper orderMapper;

    /**
     * 每日 0:00 执行：到期入住 + 到期完成。
     *
     * 已被 RabbitMQ 延迟消息替代，保留此定时任务作为兜底方案。
     * 正常情况由 OrderMessageListener 消费延迟消息触发状态流转；
     * 若 RabbitMQ 故障导致消息丢失，每日 0 点全表扫描补漏。
     * 启用时取消注释 @Scheduled 即可。
     */
    // @Scheduled(cron = "0 0 0 * * ?")
    public void autoUpdateOrderStatus() {
        LocalDate today = LocalDate.now();
        log.info("定时任务：开始扫描订单状态更新，当前日期 {}", today);

        // 1. 已支付 → 寄养中：入住日期已到且状态为 paid
        List<Order> toBoarding = orderMapper.selectList(
                Wrappers.<Order>lambdaQuery()
                        .eq(Order::getStatus, OrderStatus.paid)
                        .le(Order::getStartDate, today));
        for (Order order : toBoarding) {
            order.setStatus(OrderStatus.boarding);
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
        }
        log.info("定时任务：paid → boarding 共处理 {} 笔订单", toBoarding.size());

        // 2. 寄养中 → 已完成：离店日期已过且状态为 boarding
        List<Order> toComplete = orderMapper.selectList(
                Wrappers.<Order>lambdaQuery()
                        .eq(Order::getStatus, OrderStatus.boarding)
                        .lt(Order::getEndDate, today));
        for (Order order : toComplete) {
            order.setStatus(OrderStatus.completed);
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
        }
        log.info("定时任务：boarding → completed 共处理 {} 笔订单", toComplete.size());
    }
}
