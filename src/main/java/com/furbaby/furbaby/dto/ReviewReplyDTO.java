package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "商家回复评价请求")
public class ReviewReplyDTO {

    @NotNull(message = "评价ID不能为空")
    @Schema(description = "评价ID", required = true)
    private Long reviewId;

    @NotBlank(message = "回复内容不能为空")
    @Schema(description = "回复内容", required = true)
    private String content;
}
