package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家注册响应")
public class ShopRegisterVO {

    @Schema(description = "商家ID")
    private Long shopId;

    @Schema(description = "状态")
    private String status;
}
