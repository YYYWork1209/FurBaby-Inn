package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "状态步骤")
public class StatusStepVO {

    @Schema(description = "状态")
    private String status;

    @Schema(description = "时间")
    private LocalDateTime time;
}
