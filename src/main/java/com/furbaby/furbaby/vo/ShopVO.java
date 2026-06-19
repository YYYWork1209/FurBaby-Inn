package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家卡片")
public class ShopVO {

    @Schema(description = "商家ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "评分")
    private Double rating;

    @Schema(description = "日单价")
    private BigDecimal price;

    @Schema(description = "标签")
    private List<String> tags;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "距离")
    private Double distance;
}
