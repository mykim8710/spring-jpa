package com.example.springjpa.global.error;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BusinessRollBackException extends RuntimeException {
    private GlobalErrorCode globalErrorCode;

    public BusinessRollBackException(GlobalErrorCode globalErrorCode) {
        this.globalErrorCode = globalErrorCode;
    }
}
