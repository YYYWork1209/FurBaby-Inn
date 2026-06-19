package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "退款申请")
public class RefundDTO {

    @NotNull(message = "订单ID不能为空")
    @Schema(description = "订单ID", example = "1", required = true)
    private Long orderId;

    @Schema(description = "退款原因", example = "商家无法接待")
    private String reason;
}
