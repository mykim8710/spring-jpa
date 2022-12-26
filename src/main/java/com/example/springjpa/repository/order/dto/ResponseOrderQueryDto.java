package com.example.springjpa.repository.order.dto;

import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.OrderItem;
import com.example.springjpa.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ResponseOrderQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;
    private List<ResponseOrderItemQueryDto> orderItems;

    public ResponseOrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
                                 OrderStatus orderStatus, Address address, List<ResponseOrderItemQueryDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }

    public ResponseOrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
                                 OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public void setOrderItems(List<ResponseOrderItemQueryDto> orderItems) {
        this.orderItems = orderItems;
    }
}
