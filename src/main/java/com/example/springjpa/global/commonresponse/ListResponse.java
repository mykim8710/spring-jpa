package com.example.springjpa.global.commonresponse;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class ListResponse<T> extends CommonResponse {
    private List<T> dataList;
}