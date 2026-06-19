package com.furbaby.furbaby.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@Schema(description = "商家档期")
public class ShopSchedule {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "商家ID")
    private Long shopId;

    @Schema(description = "日期")
    private LocalDate date;

    @Builder.Default
    @Schema(description = "剩余名额")
    private Integer available = 0;

    @Schema(description = "当日价格")
    private BigDecimal price;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
