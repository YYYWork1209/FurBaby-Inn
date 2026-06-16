package com.furbaby.furbaby.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class ShopRegisterDTO {

    @NotBlank(message = "商家名称不能为空")
    private String name;

    @NotBlank(message = "联系电话不能为空")
    private String phone;

    @NotBlank(message = "地址不能为空")
    private String address;

    private String description;
    private List<String> tags;
    private List<String> services;
}
