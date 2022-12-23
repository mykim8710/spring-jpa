package com.example.springjpa.api.dto.order;

import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.Order;
import com.example.springjpa.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class ResponseOrderSelectDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<ResponseOrderItemSelectDto> orderItems;

    public ResponseOrderSelectDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems()
                                        .stream()
                                        .map(orderItem -> new ResponseOrderItemSelectDto(orderItem))
                                        .collect(toList());
    }
}
