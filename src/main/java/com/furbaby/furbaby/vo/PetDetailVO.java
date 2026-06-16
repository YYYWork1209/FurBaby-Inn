package com.furbaby.furbaby.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDetailVO {

    private Long id;
    private String name;
    private String species;
    private String breed;
    private String avatar;
    private Integer age;
    private String gender;
    private BigDecimal weight;
    private String healthNotes;
    private List<VaccineVO> vaccines;
    private LocalDateTime createTime;
}
