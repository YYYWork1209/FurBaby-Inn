package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "单日档期")
public class ScheduleVO {

    @Schema(description = "日期")
    private LocalDate date;

    @Schema(description = "剩余名额")
    private Integer available;

    @Schema(description = "价格")
    private BigDecimal price;
}
