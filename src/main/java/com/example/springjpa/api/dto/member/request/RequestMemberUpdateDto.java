package com.example.springjpa.api.dto.member.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestMemberUpdateDto {
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
