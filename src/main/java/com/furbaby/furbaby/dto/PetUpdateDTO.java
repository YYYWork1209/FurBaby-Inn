package com.furbaby.furbaby.dto;

import com.furbaby.furbaby.entity.Pet.Gender;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PetUpdateDTO {

    private String name;
    private String species;
    private String breed;
    private String avatar;
    private Gender gender;
    private Integer age;
    private BigDecimal weight;
    private String healthNotes;
}
