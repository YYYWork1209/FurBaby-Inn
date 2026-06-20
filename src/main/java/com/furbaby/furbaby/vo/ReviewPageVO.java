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
@Schema(description = "评价分页")
public class ReviewPageVO {

    @Schema(description = "总数")
    private Long total;

    @Schema(description = "平均评分")
    private Double avgRating;

    @Schema(description = "评价列表")
    private List<ReviewItemVO> records;
}
