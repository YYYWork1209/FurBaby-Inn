package com.furbaby.furbaby.dto;

import com.furbaby.furbaby.enums.PayMethod;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建支付")
public class PaymentCreateDTO {

    @NotNull(message = "订单ID不能为空")
    @Schema(description = "订单ID", example = "1", required = true)
    private Long orderId;

    @NotNull(message = "支付方式不能为空")
    @Schema(description = "支付方式", required = true)
    private PayMethod payMethod;
}
