package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家后台统计数据")
public class DashboardStatsVO {

    @Schema(description = "今日新订单数")
    private Long todayOrders;

    @Schema(description = "今日营业额")
    private BigDecimal todayRevenue;

    @Schema(description = "待处理订单数")
    private Long pendingOrders;

    @Schema(description = "寄养中数量")
    private Long boardingCount;

    @Schema(description = "累计评价数")
    private Long totalReviews;

    @Schema(description = "平均评分")
    private Double avgRating;
}
