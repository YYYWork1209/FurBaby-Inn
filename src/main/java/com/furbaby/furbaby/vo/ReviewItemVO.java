package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "评价详情")
public class ReviewItemVO {

    @Schema(description = "评价ID")
    private Long reviewId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "评分")
    private Integer rating;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "照片列表")
    private List<String> photos;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
