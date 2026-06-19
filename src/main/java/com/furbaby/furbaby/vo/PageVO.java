package com.furbaby.furbaby.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页响应")
public class PageVO<T> {

    @Schema(description = "总数")
    private Long total;

    @Schema(description = "总页数")
    private Integer pages;

    @Schema(description = "数据列表")
    private List<T> records;

    public static <T> PageVO<T> of(Long total, Integer pages, List<T> records) {
        return PageVO.<T>builder().total(total).pages(pages).records(records).build();
    }
}
