package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "宠物信息")
public class PetVO {

    @Schema(description = "宠物ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "物种")
    private String species;

    @Schema(description = "品种")
    private String breed;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "体重")
    private BigDecimal weight;
}
