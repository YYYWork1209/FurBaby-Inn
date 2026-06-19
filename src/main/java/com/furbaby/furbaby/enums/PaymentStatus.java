package com.furbaby.furbaby.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "支付状态")
public enum PaymentStatus {
    pending, success, failed, closed
}
