package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "取消订单")
public class OrderCancelDTO {

    @Schema(description = "取消原因", example = "行程变更")
    private String reason;
}
