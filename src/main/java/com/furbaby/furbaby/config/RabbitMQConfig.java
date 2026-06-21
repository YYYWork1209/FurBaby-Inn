package com.furbaby.furbaby.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 拓扑定义。
 *
 * 延迟消息采用 DLX + TTL 方案（不依赖 rabbitmq-delayed-message-exchange 插件）：
 *   消息 → delay.queue（带 TTL）→ 过期 → DLX → process.queue → 消费者
 *
 * 事件广播采用 TopicExchange 异步通知。
 */
@Configuration
public class RabbitMQConfig {

    // ============ 延迟消息组件（DLX + TTL） ============

    /** 死信交换机：接收过期消息 */
    public static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    /** 死信路由键 */
    public static final String ORDER_DLX_ROUTING_KEY = "order.process";
    /** 延迟队列：消息在此等待 TTL 过期，不设消费者 */
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    /** 处理队列：接收 DLX 转发的过期消息 */
    public static final String ORDER_PROCESS_QUEUE = "order.process.queue";

    @Bean
    public DirectExchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE);
    }

    @Bean
    public Queue orderProcessQueue() {
        return QueueBuilder.durable(ORDER_PROCESS_QUEUE).build();
    }

    @Bean
    public Binding orderProcessBinding() {
        return BindingBuilder.bind(orderProcessQueue())
                .to(orderDlxExchange())
                .with(ORDER_DLX_ROUTING_KEY);
    }

    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder.durable(ORDER_DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ORDER_DLX_ROUTING_KEY)
                .build();
    }

    // ============ 事件广播组件 ============

    public static final String ORDER_EVENT_EXCHANGE = "order.event.exchange";
    public static final String ORDER_EVENT_QUEUE = "order.event.queue";
    public static final String ORDER_EVENT_ROUTING_KEY = "order.event.#";

    @Bean
    public TopicExchange orderEventExchange() {
        return new TopicExchange(ORDER_EVENT_EXCHANGE);
    }

    @Bean
    public Queue orderEventQueue() {
        return QueueBuilder.durable(ORDER_EVENT_QUEUE).build();
    }

    @Bean
    public Binding orderEventBinding() {
        return BindingBuilder.bind(orderEventQueue())
                .to(orderEventExchange())
                .with(ORDER_EVENT_ROUTING_KEY);
    }

    // ============ 消息序列化 ============

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
