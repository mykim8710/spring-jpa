package com.example.springjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "ORDERS")
public class Order { // N
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // MEMBER - ORDER, 1 : N
    // 연관관계의 주인 : ORDER가 MEMBER_ID(fk)를 가짐
    @ManyToOne(fetch = FetchType.LAZY)   // 지연로딩 전략(@ManyToOne, @OneToOne)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // ORDER - DELIVERY, 1 : 1
    // 연관관계의 주인 : ORDER가 DELIVERY_ID(fk)를 가짐
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)   // 지연로딩 전략(@ManyToOne, @OneToOne)
    @JoinColumn(name = "DELIVERY_ID")                              // cascade = CascadeType.ALL, order저장 또는 삭제시 delivery도 같이
    private Delivery delivery;

    // [양방향 매핑]
    // ORDER - ORDER_ITEM, 1 : N
    // 연관관계의 주인 : ORDER_ITEM이 ORDER_ID(fk)를 가짐
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

     /* cascade = CascadeType.ALL EX, order 저장 또는 삭제될때, orderItem도 같이 된다.
     OrderItem orderItemA = new OrderItem();
     OrderItem orderItemB = new OrderItem();
     OrderItem orderItemC = new OrderItem();
     Order order = new Order();
     order.setOrderItems(orderItemA);
     order.setOrderItems(orderItemB);
     order.setOrderItems(orderItemC);

     // em.persist(orderItemA); // cascade = CascadeType.ALL, 해줄필요 없음
     // em.persist(orderItemB); // ALL이기 떄문에 insert, delete 모두 적용됨
     // em.persist(orderItemC);

     em.persist(order);
     */

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // ORDER, CANCEL


    // 양방향 편의 메서드, ORDER - MEMBER, N : 1
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // 양방향 편의 메서드, ORDER - ORDER_ITEM, 1 : N
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 양방향 편의 메서드, ORDER - DELIVERY, 1 : 1
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
