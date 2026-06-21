package com.furbaby.furbaby.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单延迟消息体。
 *
 * type 决定消费端执行哪种状态流转：
 *   TIMEOUT_CANCEL   — pending 超时未支付，自动取消
 *   START_BOARDING   — 到达寄养开始日期，paid → boarding
 *   COMPLETE_BOARDING — 超过寄养结束日期，boarding → completed
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {

    private Long orderId;
    private Long shopId;
    private String type;
    private LocalDateTime eventTime;
}
