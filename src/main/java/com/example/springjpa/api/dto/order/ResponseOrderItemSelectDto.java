package com.example.springjpa.api.dto.order;


import com.example.springjpa.domain.OrderItem;
import lombok.Getter;

@Getter
public class ResponseOrderItemSelectDto {
    private String itemName;//상품 명
    private int orderPrice; //주문 가격
    private int count; //주문 수량

    public ResponseOrderItemSelectDto(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
