package com.example.springjpa.domain;

import com.example.springjpa.domain.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JsonIgnore
    private Order order;

    // ITEM - ORDER_ITEM, 1 : N
    // 연관관계의 주인 : ORDER_ITEM이 ITEM_ID(fk)를 가짐
    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩 전략(@ManyToOne, @OneToOne)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;
    private int count;

    /**
     * 생성 메서드 : 주문상품
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);    // 재고
        return orderItem;
    }

    // 비지니스 로직
    /**
     * 주문 취소 : 재고원복 */
    public void cancel() {
        //getItem().addStock(this.count);
        //this.getItem().addStock(this.count);
        this.item.addStock(this.count);
    }


    /**
     * 주문상품 전체 가격 조회 */
    public int getOrderItemTotalPrice() {
        //return getOrderPrice() * getCount();
        //return this.getOrderPrice() * this.getCount();
        return this.orderPrice * this.count;
    }
}
