package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "档期批量设置")
public class ScheduleDTO {

    @NotEmpty(message = "档期列表不能为空")
    @Schema(description = "档期列表", required = true)
    private List<ScheduleItem> dates;

    @Data
    @Schema(description = "单个档期项")
    public static class ScheduleItem {

        @Schema(description = "日期", example = "2026-06-20")
        private String date;

        @Schema(description = "可用名额", example = "5")
        private Integer available;

        @Schema(description = "价格", example = "199.00")
        private java.math.BigDecimal price;
    }
}
