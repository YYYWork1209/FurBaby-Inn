package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "疫苗记录")
public class VaccineVO {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "日期")
    private LocalDate date;
}
