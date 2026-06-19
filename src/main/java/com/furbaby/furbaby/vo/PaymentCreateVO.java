package com.furbaby.furbaby.vo;

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
@Schema(description = "支付创建响应")
public class PaymentCreateVO {

    @Schema(description = "支付ID")
    private Long paymentId;

    @Schema(description = "支付单号")
    private String payNo;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "收款二维码")
    private String qrCode;

    @Schema(description = "支付链接")
    private String payUrl;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;
}
