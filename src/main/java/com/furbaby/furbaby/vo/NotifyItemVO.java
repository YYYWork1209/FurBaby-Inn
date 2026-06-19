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
@Schema(description = "通知")
public class NotifyItemVO {

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "是否已读")
    private Boolean read;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
