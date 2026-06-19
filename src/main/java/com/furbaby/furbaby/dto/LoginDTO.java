package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求")
public class LoginDTO {

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", example = "13800138000", required = true)
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "abc123", required = true)
    private String password;
}
