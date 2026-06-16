package com.furbaby.furbaby.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefundDTO {

    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    private String reason;
}
