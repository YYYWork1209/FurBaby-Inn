package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单详情")
public class OrderDetailVO {

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "商家名称")
    private String shopName;

    @Schema(description = "宠物名称")
    private String petName;

    @Schema(description = "开始日期")
    private LocalDate startDate;

    @Schema(description = "结束日期")
    private LocalDate endDate;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "商家ID")
    private Long shopId;

    @Schema(description = "宠物ID")
    private Long petId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;
}
