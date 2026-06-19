package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "商家注册请求")
public class ShopRegisterDTO {

    @NotBlank(message = "商家名称不能为空")
    @Schema(description = "商家名称", example = "宠爱有家宠物寄养中心", required = true)
    private String name;

    @NotBlank(message = "联系电话不能为空")
    @Schema(description = "联系电话", example = "13800138000", required = true)
    private String phone;

    @NotBlank(message = "地址不能为空")
    @Schema(description = "地址", example = "北京市朝阳区xxx路xxx号", required = true)
    private String address;

    @Schema(description = "描述", example = "专业宠物寄养，提供舒适环境与贴心服务")
    private String description;

    @Schema(description = "标签列表", example = "[\"猫咪友好\", \"户外活动\", \"24小时看护\"]")
    private List<String> tags;

    @Schema(description = "服务列表", example = "[\"寄养\", \"美容\", \"医疗护理\"]")
    private List<String> services;
}
