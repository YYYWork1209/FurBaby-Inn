package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家档期")
public class ShopScheduleVO {

    @Schema(description = "商家ID")
    private Long shopId;

    @Schema(description = "档期列表")
    private List<ScheduleVO> schedules;
}
