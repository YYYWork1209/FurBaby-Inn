package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "用户信息更新")
public class UserUpdateDTO {

    @Schema(description = "昵称", example = "宠物达人")
    private String nickname;

    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Schema(description = "邮箱", example = "user@example.com")
    private String email;
}
