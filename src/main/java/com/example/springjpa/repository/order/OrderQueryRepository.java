package com.example.springjpa.repository.order;

import com.example.springjpa.repository.order.dto.ResponseOrderItemQueryDto;
import com.example.springjpa.repository.order.dto.ResponseOrderQueryDto;
import com.example.springjpa.repository.order.dto.ResponseOrderSimpleQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class OrderQueryRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * 컬렉션은 별도로 조회
     * Query: 루트 1번, 컬렉션 N 번
     * 단건 조회에서 많이 사용하는 방식
     */
    public List<ResponseOrderQueryDto> findAllOrderQueryDtos() {
        List<ResponseOrderQueryDto> responseOrderQueryDtos = findAllOrders();

        responseOrderQueryDtos.forEach(responseOrderQueryDto ->{
            List<ResponseOrderItemQueryDto> responseOrderItemQueryDtos = findOrderItems(responseOrderQueryDto.getOrderId());
            responseOrderQueryDto.setOrderItems(responseOrderItemQueryDtos);
        });

        return responseOrderQueryDtos;
    }


    /**
     * 1:N 관계(컬렉션)를 제외한 나머지를 한번에 조회
     */
    private List<ResponseOrderQueryDto> findAllOrders() {
        String jpql = "select new com.example.springjpa.repository.order.dto.ResponseOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d";

        return em.createQuery(jpql, ResponseOrderQueryDto.class).getResultList();
    }

    /**
     * 1:N 관계인 orderItems 조회
     */
    private List<ResponseOrderItemQueryDto> findOrderItems(Long orderId) {
        String jpql = "select new com.example.springjpa.repository.order.dto.ResponseOrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                            " from OrderItem oi" +
                            " join oi.item i" +
                            " where oi.order.id = :orderId";

        return em.createQuery(jpql, ResponseOrderItemQueryDto.class)
                    .setParameter("orderId", orderId)
                    .getResultList();
    }


}
