package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "退款响应")
public class RefundVO {

    @Schema(description = "退款ID")
    private Long refundId;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "退款金额")
    private BigDecimal amount;
}
