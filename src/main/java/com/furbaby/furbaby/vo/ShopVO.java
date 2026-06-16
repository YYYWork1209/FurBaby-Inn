package com.furbaby.furbaby.vo;

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
public class ShopVO {

    private Long id;
    private String name;
    private String avatar;
    private Double rating;
    private BigDecimal price;
    private List<String> tags;
    private String address;
    private Double distance;
}
