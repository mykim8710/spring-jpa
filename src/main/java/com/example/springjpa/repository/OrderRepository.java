package com.example.springjpa.repository;

import com.example.springjpa.domain.Member;
import com.example.springjpa.domain.Order;
import com.example.springjpa.domain.OrderSearch;
import com.example.springjpa.service.OrderService;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
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

    // 주문 전체 조회 : JPQL로 처리 -> 실무 사용 X
    public List<Order> findAllByJPQL(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문상태 검색
        if(orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }

            jpql += " o.status = :status";
        }

        // 회원이름 검색
        if(orderSearch.getMemberName() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }

            jpql += " m.name = :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                                    .setMaxResults(1000);   // 최대 1000건

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }


    // 주문 전체 조회 : JPQL Criteria 처리 -> 실무 사용 X
    public List<Order> findAllByJPQLCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }
}
