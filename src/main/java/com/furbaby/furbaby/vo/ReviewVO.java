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
@Schema(description = "评价")
public class ReviewVO {

    @Schema(description = "评价ID")
    private Long reviewId;

    @Schema(description = "评分")
    private Integer rating;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
