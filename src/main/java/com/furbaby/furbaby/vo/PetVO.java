package com.furbaby.furbaby.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetVO {

    private Long id;
    private String name;
    private String species;
    private String breed;
    private String avatar;
    private Integer age;
    private String gender;
    private BigDecimal weight;
}
