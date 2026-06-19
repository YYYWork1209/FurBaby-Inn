package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "下单请求")
public class OrderCreateDTO {

    @NotNull(message = "商家ID不能为空")
    @Schema(description = "商家ID", example = "1", required = true)
    private Long shopId;

    @NotNull(message = "宠物ID不能为空")
    @Schema(description = "宠物ID", example = "1", required = true)
    private Long petId;

    @NotNull(message = "开始日期不能为空")
    @Schema(description = "入住日期", example = "2026-06-20", required = true)
    private LocalDate startDate;

    @NotNull(message = "结束日期不能为空")
    @Schema(description = "离店日期", example = "2026-06-25", required = true)
    private LocalDate endDate;

    @Schema(description = "备注", example = "请每天遛狗两次")
    private String remark;
}
