package com.example.springjpa.config.security.handler;

import com.example.springjpa.global.commonresponse.CommonResponse;
import com.example.springjpa.global.commonresponse.CommonResponseService;
import com.example.springjpa.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final CommonResponseService commonResponseService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("Sign-in Fail");
        // 해당계정이 없을때
        // UsernameNotFoundException( < AuthenticationException < RuntimeException)
        if (exception instanceof UsernameNotFoundException) {
            sendResponseError(response, GlobalErrorCode.NOT_FOUND_USER);
        }

        // 비밀번호가 틀릴때
        // BadCredentialsException( < AuthenticationException < RuntimeException)
        if (exception instanceof BadCredentialsException) {
            sendResponseError(response, GlobalErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    private void sendResponseError(HttpServletResponse response, GlobalErrorCode globalErrorCode) throws HttpMessageNotWritableException, IOException {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        CommonResponse errorResponse = commonResponseService.getCommonResponse(globalErrorCode.getStatus(), globalErrorCode.getCode(), globalErrorCode.getMessage());

        if(jsonConverter.canWrite(errorResponse.getClass(), jsonMimeType)) {
            jsonConverter.write(errorResponse, jsonMimeType, new ServletServerHttpResponse(response));
        }
    }
}
