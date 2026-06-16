package com.furbaby.furbaby.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class ScheduleDTO {

    @NotEmpty(message = "档期列表不能为空")
    private List<ScheduleItem> dates;

    @Data
    public static class ScheduleItem {
        private String date;
        private Integer available;
        private java.math.BigDecimal price;
    }
}
