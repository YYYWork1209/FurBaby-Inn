package com.furbaby.furbaby.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 响应结果实体类,用于封装响应结果
 * 包含状态码、消息和数据
 * 状态码: 200 成功, 500 失败
 * 消息: 成功或失败的描述
 * 数据: 成功时返回的数据列表
 * 提供成功和失败的静态方法,用于快速创建成功或失败的响应结果
 * @param <T>
 */
@Data
@AllArgsConstructor
@Builder
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public static <E> Result<E> success() {
        return Result.<E>builder()
                .code("200")
                .message("成功")
                .data(null)
                .build();
    }

    public static <E> Result<E> success(E data) {
        return Result.<E>builder()
                .code("200")
                .message("成功")
                .data(data)
                .build();
    }

    public static <E> Result<E> error(String message) {
        return Result.<E>builder()
                .code("500")
                .message(message)
                .data(null)
                .build();
    }

}
