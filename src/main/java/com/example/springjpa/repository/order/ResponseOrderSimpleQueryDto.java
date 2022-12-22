package com.example.springjpa.repository.order;

import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseOrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;

    public ResponseOrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
