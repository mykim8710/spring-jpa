package com.example.springjpa.repository;

import com.example.springjpa.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepository {
    @PersistenceContext // JPA 스펙에서 제공하는 기능, 영속성 컨텍스트를 주입하는 표준 애노테이션
    private EntityManager em;

    // 상품등록
    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    // 상품 단건 조회
    public Optional<Item> findById(Long itemId) {
        return Optional.ofNullable(em.find(Item.class, itemId));
    }

    // 상품 전체 조회
    public List<Item> findAll() {
        String jpql = "select i from Item i";
        return em.createQuery(jpql, Item.class)
                 .getResultList();
    }
}