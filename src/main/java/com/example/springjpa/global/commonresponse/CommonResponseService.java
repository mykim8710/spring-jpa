package com.example.springjpa.global.commonresponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommonResponseService {
    public<T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse singleResponse = SingleResponse.builder()
                                                            .data(data)
                                                            .build();
        setSuccessResponse(singleResponse);
        return singleResponse;
    }

    public<T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse listResponse = ListResponse.builder()
                                                    .dataList((List<Object>) dataList)
                                                    .build();
        setSuccessResponse(listResponse);
        return listResponse;
    }


    public CommonResponse getCommonResponse(int status, String code, String message) {
        return new CommonResponse(status, code, message);
    }

    void setSuccessResponse(CommonResponse response) {
        response.setStatus(HttpStatus.OK.value());
        response.setCode("");
        response.setMessage("OK");
    }
}
