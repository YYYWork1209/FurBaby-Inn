package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付状态")
public class PaymentStatusVO {

    @Schema(description = "支付ID")
    private Long paymentId;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "支付完成时间")
    private LocalDateTime payTime;
}
