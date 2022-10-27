package com.example.springjpa.global.commonresponse;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class SingleResponse<T> extends CommonResponse {
    private T data;
}