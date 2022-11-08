package com.example.springjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 주문 회원

    private List<OrderItem> orderItems = new ArrayList<>();

    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간

    private OrderStatus status; //주문상태 [ORDER, CANCEL]


}
