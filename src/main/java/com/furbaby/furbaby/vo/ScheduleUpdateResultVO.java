package com.furbaby.furbaby.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateResultVO {
    private Boolean success;
    private Integer available;
}
