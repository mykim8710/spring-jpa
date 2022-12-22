package com.example.springjpa.global.result;

import lombok.Data;

@Data
public class CommonResult<T> {
    private int status;
    private String message;
    private T data;

    public CommonResult(T data) {
        this.status = 200;
        this.message = "OK";
        this.data = data;
    }
}
