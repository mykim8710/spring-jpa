package com.example.springjpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Getter @Setter
@Entity
public class Delivery {
    @Id @GeneratedValue
    @Column(name="DELIVERY_ID")
    private Long id;

    // [양방향 매핑]
    // ORDER - DELIVERY, 1 : 1
    // 연관관계의 주인 : ORDER가 DELIVERY_ID(fk)를 가짐
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;  // READY, COMP
}
