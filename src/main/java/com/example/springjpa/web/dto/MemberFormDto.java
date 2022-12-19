package com.example.springjpa.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    @NotEmpty(message = "도시를 입력해주세요.")
    private String city;

    @NotEmpty(message = "거리를 입력해주세요.")
    private String street;

    @NotEmpty(message = "우편번호를 입력해주세요.")
    private String zipcode;
}

