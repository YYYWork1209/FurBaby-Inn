package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "档期更新")
public class ScheduleUpdateDTO {

    @NotBlank(message = "日期不能为空")
    @Schema(description = "日期", example = "2026-06-20", required = true)
    private String date;

    @NotNull(message = "扣减数量不能为空")
    @Schema(description = "名额变化量", example = "1", required = true)
    private Integer delta;
}
