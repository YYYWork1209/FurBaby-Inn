package com.furbaby.furbaby.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "订单状态")
public enum OrderStatus {
    pending, paid, boarding, completed, cancelled, refunding, refunded
}
