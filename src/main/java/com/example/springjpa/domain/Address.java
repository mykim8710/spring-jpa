package com.example.springjpa.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장타입으로 사용할 객체
@Getter
public class Address {
    private String city;
    private String street;
    private String zipCode;
}
