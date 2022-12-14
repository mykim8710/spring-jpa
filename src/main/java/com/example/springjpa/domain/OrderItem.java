package com.example.springjpa.domain;

import com.example.springjpa.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    // ORDER - ORDER_ITEM, 1 : N
    // 연관관계의 주인 : ORDER_ITEM이 ORDER_ID(fk)를 가짐
    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩 전략(@ManyToOne, @OneToOne)
    @JoinColumn(name="ORDER_ID")
    private Order order;

    // ITEM - ORDER_ITEM, 1 : N
    // 연관관계의 주인 : ORDER_ITEM이 ITEM_ID(fk)를 가짐
    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩 전략(@ManyToOne, @OneToOne)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;
    private int count;
}
