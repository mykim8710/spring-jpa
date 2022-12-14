package com.example.springjpa.domain.item;

import com.example.springjpa.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계 전략 : 싱글테이블 전략
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // [다대다 양방향 매핑] : 실무에선 사용 X
    // ITEM - ITEM_CATEGORY, M : N
    // 연관관계의 주인 : CATEGORY로 설정
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
