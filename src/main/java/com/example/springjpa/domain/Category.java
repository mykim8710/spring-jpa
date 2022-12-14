package com.example.springjpa.domain;

import com.example.springjpa.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Category {
    @Id @GeneratedValue
    @Column(name= "CATEGORY_ID")
    private Long id;

    private String name;

    // [다대다 양방향 매핑] : 실무에선 사용 X
    // ITEM - CATEGORY, M : N
    // 연관관계의 주인 : CATEGORY로 설정
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
        joinColumns = @JoinColumn(name = "CATEGORY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )  // 중간테이블 매핑
    private List<Item> items = new ArrayList<>();

    // 부모 카테고리 매핑 : 같은 엔티티끼리 양방향 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    // 자식 카테고리 매핑 : 같은 엔티티끼리 양방향 연관관계 매핑
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 양방향 편의 메서드, CATEGORY - CATEGORY
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
