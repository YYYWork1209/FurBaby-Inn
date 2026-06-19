package com.furbaby.furbaby.dto;

import com.furbaby.furbaby.enums.PetGender;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "宠物信息更新")
public class PetUpdateDTO {

    @Schema(description = "名称", example = "小黑")
    private String name;

    @Schema(description = "物种", example = "狗")
    private String species;

    @Schema(description = "品种", example = "金毛")
    private String breed;

    @Schema(description = "头像URL", example = "https://example.com/pet.jpg")
    private String avatar;

    @Schema(description = "性别")
    private PetGender gender;

    @Schema(description = "年龄（岁）", example = "3")
    private Integer age;

    @Schema(description = "体重（kg）", example = "12.5")
    private BigDecimal weight;

    @Schema(description = "健康备注", example = "已接种疫苗，无过敏史")
    private String healthNotes;
}
