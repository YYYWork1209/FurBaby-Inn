package com.furbaby.furbaby.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "支付方式")
public enum PayMethod {
    wechat, alipay
}
