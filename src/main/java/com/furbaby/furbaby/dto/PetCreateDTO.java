package com.furbaby.furbaby.dto;

import com.furbaby.furbaby.enums.PetGender;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "宠物创建")
public class PetCreateDTO {

    @NotBlank(message = "宠物名称不能为空")
    @Schema(description = "名称", example = "小黑", required = true)
    private String name;

    @NotBlank(message = "物种不能为空")
    @Schema(description = "物种", example = "狗", required = true)
    private String species;

    @NotBlank(message = "品种不能为空")
    @Schema(description = "品种", example = "金毛", required = true)
    private String breed;

    @Schema(description = "头像URL", example = "https://example.com/pet.jpg")
    private String avatar;

    @NotNull(message = "性别不能为空")
    @Schema(description = "性别", required = true)
    private PetGender gender;

    @NotNull(message = "年龄不能为空")
    @Schema(description = "年龄（岁）", example = "3", required = true)
    private Integer age;

    @NotNull(message = "体重不能为空")
    @Schema(description = "体重（kg）", example = "12.5", required = true)
    private BigDecimal weight;

    @Schema(description = "健康备注", example = "已接种疫苗，无过敏史")
    private String healthNotes;
}
