package com.furbaby.furbaby.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetailVO {

    private Long id;
    private String name;
    private String avatar;
    private List<String> photos;
    private Double rating;
    private BigDecimal price;
    private List<String> tags;
    private String address;
    private String phone;
    private String description;
    private List<String> services;
    private String notice;
}
