package com.example.springjpa.global.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum GlobalErrorCode {
    // sign in error
    // NOT_FOUND_USER(400, "L001", "This account does not exist."),
    // NOT_MATCH_PASSWORD(400, "L002", "Passwords do not match."),
    ;


    private int status;
    private String code;
    private String message;


    GlobalErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
