package com.furbaby.furbaby.pet.dto;

import com.furbaby.furbaby.pet.entity.Pet.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PetCreateDTO {

    @NotBlank(message = "宠物名称不能为空")
    private String name;

    @NotBlank(message = "物种不能为空")
    private String species;

    @NotBlank(message = "品种不能为空")
    private String breed;

    private String avatar;

    @NotNull(message = "性别不能为空")
    private Gender gender;

    @NotNull(message = "年龄不能为空")
    private Integer age;

    @NotNull(message = "体重不能为空")
    private BigDecimal weight;

    private String healthNotes;
}
