package com.example.springjpa.global.error;

import com.example.springjpa.global.commonresponse.CommonResponse;
import com.example.springjpa.global.commonresponse.CommonResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final CommonResponseService commonResponseService;


    /* @Service Exception */
    @ExceptionHandler(BusinessRollBackException.class)
    protected ResponseEntity<CommonResponse> businessRollbackExceptionHandle(final BusinessRollBackException e) {
        log.error(e.getGlobalErrorCode().getCode() + " : " + e.getGlobalErrorCode().getMessage());

        final GlobalErrorCode errorCode = e.getGlobalErrorCode();
        final CommonResponse errorResponse = commonResponseService.getCommonResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());

        log.error("errorCode > " +errorCode);
        log.error("response > " +errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

}
