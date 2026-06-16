package com.furbaby.furbaby.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {

    private Long total;
    private Integer pages;
    private List<T> records;

    public static <T> PageVO<T> of(Long total, Integer pages, List<T> records) {
        return PageVO.<T>builder().total(total).pages(pages).records(records).build();
    }
}
