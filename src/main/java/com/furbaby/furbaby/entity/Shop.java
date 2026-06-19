package com.furbaby.furbaby.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.furbaby.furbaby.enums.ShopStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家")
public class Shop {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "商户用户ID")
    private Long userId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "店铺照片")
    private String photos;

    @Builder.Default
    @Schema(description = "评分")
    private Double rating = 5.0;

    @Schema(description = "日单价")
    private BigDecimal price;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "服务项目")
    private String services;

    @Schema(description = "入住须知")
    private String notice;

    @Builder.Default
    @Schema(description = "状态")
    private ShopStatus status = ShopStatus.pending;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
