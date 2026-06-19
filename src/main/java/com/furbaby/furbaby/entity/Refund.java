package com.furbaby.furbaby.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.furbaby.furbaby.enums.RefundStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "退款")
public class Refund {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "支付ID")
    private Long paymentId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "原因")
    private String reason;

    @Builder.Default
    @Schema(description = "状态")
    private RefundStatus status = RefundStatus.pending;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
