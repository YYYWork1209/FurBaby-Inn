package com.furbaby.furbaby.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import java.util.List;

@Data
@Schema(description = "商家更新店铺请求")
public class ShopUpdateDTO {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "标签列表")
    private List<String> tags;

    @Schema(description = "服务列表")
    private List<String> services;

    @Schema(description = "入住须知")
    private String notice;
}
