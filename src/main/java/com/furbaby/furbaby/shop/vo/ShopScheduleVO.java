package com.furbaby.furbaby.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopScheduleVO {

    private Long shopId;
    private List<ScheduleVO> schedules;
}
