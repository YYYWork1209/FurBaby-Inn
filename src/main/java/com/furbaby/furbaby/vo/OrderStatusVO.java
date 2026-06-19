package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单状态跟踪")
public class OrderStatusVO {

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "当前状态")
    private String status;

    @Schema(description = "状态时间线")
    private List<StatusStepVO> timeline;
}
