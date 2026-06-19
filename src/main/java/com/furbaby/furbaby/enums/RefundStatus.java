package com.furbaby.furbaby.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "退款状态")
public enum RefundStatus {
    pending, success, failed
}
