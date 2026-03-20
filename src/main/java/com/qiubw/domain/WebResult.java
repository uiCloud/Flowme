package com.qiubw.domain;

import lombok.Data;

@Data
public class WebResult<T> {
    private int code;
    private String message;
    private T data;

    public WebResult() {
    }

    public WebResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public WebResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> WebResult<T> success() {
        return new WebResult<>(200, "操作成功");
    }

    public static <T> WebResult<T> success(T data) {
        return new WebResult<>(200, "操作成功", data);
    }

    public static <T> WebResult<T> success(String message, T data) {
        return new WebResult<>(200, message, data);
    }

    public static WebResult<Void> success(String message) {
        return new WebResult<>(200, message, null);
    }

    public static <T> WebResult<T> error(int code, String message) {
        return new WebResult<>(code, message);
    }

    public static <T> WebResult<T> error(String message) {
        return new WebResult<>(500, message);
    }
}
