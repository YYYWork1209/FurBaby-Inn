package com.furbaby.furbaby.dto;

import com.furbaby.furbaby.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "注册请求")
public class RegisterDTO {

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", example = "13800138000", required = true)
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "abc123", required = true)
    private String password;

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称", example = "宠物达人", required = true)
    private String nickname;

    @NotNull(message = "角色不能为空")
    @Schema(description = "角色", required = true)
    private UserRole role;
}
