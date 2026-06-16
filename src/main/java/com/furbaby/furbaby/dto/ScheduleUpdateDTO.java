package com.furbaby.furbaby.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleUpdateDTO {

    @NotBlank(message = "日期不能为空")
    private String date;

    @NotNull(message = "扣减数量不能为空")
    private Integer delta;
}
