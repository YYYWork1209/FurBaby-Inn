package com.furbaby.furbaby.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "商家状态")
public enum ShopStatus {
    pending, approved, rejected, disabled
}
