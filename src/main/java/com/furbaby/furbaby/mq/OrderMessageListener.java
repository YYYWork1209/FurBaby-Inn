package com.furbaby.furbaby.mq;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.furbaby.furbaby.config.RabbitMQConfig;
import com.furbaby.furbaby.entity.Notification;
import com.furbaby.furbaby.entity.Order;
import com.furbaby.furbaby.entity.ShopSchedule;
import com.furbaby.furbaby.enums.OrderStatus;
import com.furbaby.furbaby.mapper.NotificationMapper;
import com.furbaby.furbaby.mapper.OrderMapper;
import com.furbaby.furbaby.mapper.ShopScheduleMapper;
import com.furbaby.furbaby.service.impl.ShopServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息监听器。
 *
 * 消费 order.process.queue（延迟消息）和 order.event.queue（事件消息），
 * 手动 ACK 确保消息不丢失。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageListener {

    private final OrderMapper orderMapper;
    private final ShopScheduleMapper shopScheduleMapper;
    private final NotificationMapper notificationMapper;
    private final OrderMessageSender orderMessageSender;

    /**
     * 消费延迟消息（DLX+TTL 过期后到达）。
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_PROCESS_QUEUE, ackMode = "MANUAL")
    public void handleDelayedMessage(OrderMessage msg, Channel channel,
                                     @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("Processing delayed message: type={} orderId={}", msg.getType(), msg.getOrderId());
            switch (msg.getType()) {
                case "TIMEOUT_CANCEL" -> handleTimeoutCancel(msg);
                case "START_BOARDING" -> handleStartBoarding(msg);
                case "COMPLETE_BOARDING" -> handleCompleteBoarding(msg);
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("Failed to process message: type={} orderId={}: {}",
                    msg.getType(), msg.getOrderId(), e.getMessage());
            channel.basicNack(tag, false, true); // 重新入队重试
        }
    }

    /**
     * 消费订单事件（支付成功等）。
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_EVENT_QUEUE, ackMode = "MANUAL")
    public void handleOrderEvent(OrderMessage msg, Channel channel,
                                 @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("Processing order event: type={} orderId={}", msg.getType(), msg.getOrderId());
            if ("PAYMENT_SUCCESS".equals(msg.getType())) {
                handlePaymentSuccess(msg);
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("Failed to process order event: type={} orderId={}: {}",
                    msg.getType(), msg.getOrderId(), e.getMessage());
            channel.basicNack(tag, false, true);
        }
    }

    // ============ 延迟消息处理 ============

    private void handleTimeoutCancel(OrderMessage msg) {
        Order order = orderMapper.selectById(msg.getOrderId());
        if (order == null || order.getStatus() != OrderStatus.pending) {
            return; // 已支付或已取消，幂等跳过
        }
        order.setStatus(OrderStatus.cancelled);
        order.setCancelReason("超时未支付，系统自动取消");
        order.setCancelTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        // 恢复档期库存
        restoreSchedule(order);
        log.info("Order {} cancelled due to timeout", order.getId());
    }

    private void handleStartBoarding(OrderMessage msg) {
        Order order = orderMapper.selectById(msg.getOrderId());
        if (order == null || order.getStatus() != OrderStatus.paid) {
            return;
        }
        order.setStatus(OrderStatus.boarding);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        // 发送下一阶段消息：寄养到期自动完成
        orderMessageSender.sendCompleteBoarding(order.getId(), order.getShopId(),
                order.getEndDate().atStartOfDay());
        log.info("Order {} started boarding", order.getId());
    }

    private void handleCompleteBoarding(OrderMessage msg) {
        Order order = orderMapper.selectById(msg.getOrderId());
        if (order == null || order.getStatus() != OrderStatus.boarding) {
            return;
        }
        order.setStatus(OrderStatus.completed);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);
        log.info("Order {} completed", order.getId());
    }

    // ============ 事件处理 ============

    private void handlePaymentSuccess(OrderMessage msg) {
        Order order = orderMapper.selectById(msg.getOrderId());
        if (order == null) {
            return;
        }
        // 创建支付成功通知
        Notification notification = Notification.builder()
                .userId(order.getUserId())
                .type("payment")
                .title("支付成功")
                .content("订单 " + order.getOrderNo() + " 已支付成功，金额 ¥" + order.getAmount())
                .isRead(false)
                .createTime(LocalDateTime.now())
                .build();
        notificationMapper.insert(notification);
        log.info("Payment notification created for order {}", order.getId());
    }

    // ============ 辅助方法 ============

    private void restoreSchedule(Order order) {
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
    }

}
