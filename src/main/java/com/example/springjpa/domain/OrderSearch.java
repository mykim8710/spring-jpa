package com.example.springjpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
