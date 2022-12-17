package com.example.springjpa.repository;

import com.example.springjpa.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class OrderRepository {
    @PersistenceContext // JPA 스펙에서 제공하는 기능, 영속성 컨텍스트를 주입하는 표준 애노테이션
    private EntityManager em;

    // 주문저장
    public void save(Order order) {
        em.persist(order);
    }

    // 주문 단건 조회
    public Optional<Order> findOneById(Long orderId) {
        return Optional.ofNullable(em.find(Order.class, orderId));
    }

    // 주문 전체 조회 : 검색조건 추가
}
