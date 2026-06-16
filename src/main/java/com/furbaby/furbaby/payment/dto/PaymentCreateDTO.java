package com.furbaby.furbaby.payment.dto;

import com.furbaby.furbaby.payment.entity.Payment.PayMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentCreateDTO {

    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotNull(message = "支付方式不能为空")
    private PayMethod payMethod;
}
