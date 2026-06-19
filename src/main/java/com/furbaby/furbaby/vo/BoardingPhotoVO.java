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
@Schema(description = "寄养照片")
public class BoardingPhotoVO {

    @Schema(description = "照片ID")
    private Long photoId;

    @Schema(description = "照片URL")
    private String url;

    @Schema(description = "上传时间")
    private LocalDateTime uploadTime;

    @Schema(description = "描述")
    private String description;
}
