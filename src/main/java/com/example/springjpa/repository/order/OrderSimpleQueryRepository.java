package com.example.springjpa.repository.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class OrderSimpleQueryRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ResponseOrderSimpleQueryDto> findAllOrderDtos() {
        String jpql = "select new com.example.springjpa.repository.order.ResponseOrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)";
               jpql += " from Order o";
               jpql += " join o.member m";
               jpql += " join o.delivery d";

        return em.createQuery(jpql, ResponseOrderSimpleQueryDto.class).getResultList();
    }
}
