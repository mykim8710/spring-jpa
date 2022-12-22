package com.example.springjpa.api.dto.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseMemberSelectDto {
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @Builder
    public ResponseMemberSelectDto(Long id, String name, String city, String street, String zipcode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
