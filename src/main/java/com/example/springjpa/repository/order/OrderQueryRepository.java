package com.example.springjpa.repository.order;

import com.example.springjpa.repository.order.dto.ResponseOrderFlatDto;
import com.example.springjpa.repository.order.dto.ResponseOrderItemQueryDto;
import com.example.springjpa.repository.order.dto.ResponseOrderQueryDto;
import com.example.springjpa.repository.order.dto.ResponseOrderSimpleQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OrderQueryRepository {
    @PersistenceContext
    private EntityManager em;

    /** V6
     * Query 루트 + 컬렉션 : 1번
     */
    public List<ResponseOrderFlatDto> findAllOrderQueryDtosFlat() {
        return em.createQuery("select " +
                                "new com.example.springjpa.repository.order.dto.ResponseOrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d" +
                                " join o.orderItems oi" +
                                " join oi.item i", ResponseOrderFlatDto.class)
                                .getResultList();
    }

    /** V5
     * 최적화
     * Query: 루트 1번, 컬렉션 1번
     * 데이터를 한꺼번에 처리할 때 많이 사용하는 방식 *
     */
    public List<ResponseOrderQueryDto> findAllOrderQueryDtosOptimization() {
        // 루트 조회(toOne 코드를 모두 한번에 조회)
        List<ResponseOrderQueryDto> result = findAllOrders();

        // orderItem 컬렉션을 MAP 한방에 조회
        Map<Long, List<ResponseOrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        // 루프를 돌면서 컬렉션 추가(추가 쿼리 실행 X)
        result.forEach(responseOrderQueryDto -> {
            responseOrderQueryDto.setOrderItems(orderItemMap.get(responseOrderQueryDto.getOrderId()));
        });

        return result;
    }

    private List<Long> toOrderIds(List<ResponseOrderQueryDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    private Map<Long, List<ResponseOrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        String jpql = "select new com.example.springjpa.repository.order.dto.ResponseOrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                      " from OrderItem oi" +
                      " join oi.item i" +
                      " where oi.order.id in :orderIds";

        List<ResponseOrderItemQueryDto> orderItems = em.createQuery(jpql, ResponseOrderItemQueryDto.class)
                                                        .setParameter("orderIds", orderIds)
                                                        .getResultList();
        return orderItems
                    .stream()
                    .collect(Collectors.groupingBy(ResponseOrderItemQueryDto -> ResponseOrderItemQueryDto.getOrderId()));
    }


    /** V4
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
