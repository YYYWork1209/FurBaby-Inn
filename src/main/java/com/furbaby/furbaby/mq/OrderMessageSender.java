package com.furbaby.furbaby.mq;

import com.furbaby.furbaby.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 订单延迟消息发送器。
 *
 * 延迟原理：消息发送到 order.delay.queue（无消费者），设置 per-message TTL。
 * TTL 到期后消息自动转发到死信交换机 → order.process.queue → OrderMessageListener 消费。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageSender {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送待支付超时取消消息。TTL = 30 分钟。
     */
    public void sendTimeoutCancel(Long orderId, Long shopId) {
        OrderMessage msg = OrderMessage.builder()
                .orderId(orderId)
                .shopId(shopId)
                .type("TIMEOUT_CANCEL")
                .eventTime(LocalDateTime.now())
                .build();
        sendWithTtl(msg, Duration.ofMinutes(30));
        log.info("Sent TIMEOUT_CANCEL for order {} (TTL=30min)", orderId);
    }

    /**
     * 发送寄养开始消息。TTL = 距离 startDate 的剩余时间。
     */
    public void sendStartBoarding(Long orderId, Long shopId, LocalDateTime startDateTime) {
        long delayMs = Duration.between(LocalDateTime.now(), startDateTime).toMillis();
        if (delayMs <= 0) {
            delayMs = 1000; // 已过期则 1 秒后立即触发
        }
        OrderMessage msg = OrderMessage.builder()
                .orderId(orderId)
                .shopId(shopId)
                .type("START_BOARDING")
                .eventTime(startDateTime)
                .build();
        sendWithTtl(msg, Duration.ofMillis(delayMs));
        log.info("Sent START_BOARDING for order {} (TTL={}s)", orderId, delayMs / 1000);
    }

    /**
     * 发送寄养完成消息。TTL = 距离 endDate 次日的剩余时间。
     */
    public void sendCompleteBoarding(Long orderId, Long shopId, LocalDateTime endDateTime) {
        long delayMs = Duration.between(LocalDateTime.now(), endDateTime.plusDays(1)).toMillis();
        if (delayMs <= 0) {
            delayMs = 1000;
        }
        OrderMessage msg = OrderMessage.builder()
                .orderId(orderId)
                .shopId(shopId)
                .type("COMPLETE_BOARDING")
                .eventTime(endDateTime)
                .build();
        sendWithTtl(msg, Duration.ofMillis(delayMs));
        log.info("Sent COMPLETE_BOARDING for order {} (TTL={}s)", orderId, delayMs / 1000);
    }

    /**
     * 发送订单事件（支付成功等）到 topic 交换机。
     */
    public void sendOrderEvent(OrderMessage msg) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EVENT_EXCHANGE,
                "order.event.paid",
                msg);
        log.info("Sent order event: type={} orderId={}", msg.getType(), msg.getOrderId());
    }

    private void sendWithTtl(OrderMessage msg, Duration ttl) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_DELAY_QUEUE,
                msg,
                message -> {
                    message.getMessageProperties().setExpiration(String.valueOf(ttl.toMillis()));
                    return message;
                });
    }
}
