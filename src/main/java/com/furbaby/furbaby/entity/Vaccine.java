package com.furbaby.furbaby.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "疫苗记录")
public class Vaccine {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "宠物ID")
    private Long petId;

    @Schema(description = "疫苗名称")
    private String name;

    @Schema(description = "接种日期")
    private LocalDate date;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
