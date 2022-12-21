package com.example.springjpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    // [양방향 매핑]
    // MEMBER - ORDER, 1 : N
    // 연관관계의 주인은 ORDER, MEMBER_ID(fk)
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Embedded
    private Address address;

//    @Enumerated(EnumType.STRING)
//    private MemberType type;    // ADMIN, SELLER, BUYER
}
