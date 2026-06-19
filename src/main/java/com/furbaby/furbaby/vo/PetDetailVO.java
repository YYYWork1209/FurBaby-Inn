package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "宠物详情")
public class PetDetailVO {

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

    @Schema(description = "健康备注")
    private String healthNotes;

    @Schema(description = "疫苗记录列表")
    private List<VaccineVO> vaccines;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
