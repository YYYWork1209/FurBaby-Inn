package com.furbaby.furbaby.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.furbaby.furbaby.enums.PayMethod;
import com.furbaby.furbaby.enums.PaymentStatus;
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
@Schema(description = "支付")
public class Payment {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "支付单号")
    private String paymentNo;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "支付方式")
    private PayMethod payMethod;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Builder.Default
    @Schema(description = "状态")
    private PaymentStatus status = PaymentStatus.pending;

    @Schema(description = "收款二维码")
    private String qrCode;

    @Schema(description = "支付链接")
    private String payUrl;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
