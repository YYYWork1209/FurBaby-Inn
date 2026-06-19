package com.furbaby.furbaby.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.furbaby.furbaby.enums.PetGender;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "宠物")
public class Pet {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "宠主ID")
    private Long ownerId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "物种")
    private String species;

    @Schema(description = "品种")
    private String breed;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private PetGender gender;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "体重")
    private BigDecimal weight;

    @Schema(description = "健康备注")
    private String healthNotes;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
