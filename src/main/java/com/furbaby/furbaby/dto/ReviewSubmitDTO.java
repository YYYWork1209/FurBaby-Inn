package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "提交评价")
public class ReviewSubmitDTO {

    @NotNull(message = "订单ID不能为空")
    @Schema(description = "订单ID", example = "1", required = true)
    private Long orderId;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最低1分")
    @Max(value = 5, message = "评分最高5分")
    @Schema(description = "评分（1-5）", example = "5", required = true)
    private Integer rating;

    @Schema(description = "评价内容", example = "服务非常贴心，宠物很满意")
    private String content;

    @Schema(description = "评价图片URL列表", example = "[\"https://example.com/img1.jpg\", \"https://example.com/img2.jpg\"]")
    private List<String> photos;
}
