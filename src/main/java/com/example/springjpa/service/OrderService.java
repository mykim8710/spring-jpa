package com.example.springjpa.service;

import com.example.springjpa.domain.*;
import com.example.springjpa.domain.item.Item;
import com.example.springjpa.repository.ItemRepository;
import com.example.springjpa.repository.MemberRepositoryOld;
import com.example.springjpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepositoryOld memberRepository;
    private final ItemRepository itemRepository;

    // 주문 : 상품 하나씩만,,,
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회 : Member, Item
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        Item item = itemRepository.findOneById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        // 배송정보 생성 : Cascade: All, Order와 함께 Persist
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문상품 생성 : Cascade: All, Order와 함께 Persist
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문생성
        Order order = Order.createOrder(member, delivery,orderItem);

        // 주문저장
        orderRepository.save(order);
        return order.getId();
    }

    // 주문취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문조회
        Order findOrder = orderRepository.findOneById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        // 주문취소
        findOrder.cancelOrder();
    }

    // 주문조회(검색) : JPQL
    public List<Order> searchOrder(OrderSearch orderSearch) {
        return orderRepository.findAllByJPQL(orderSearch);
    }

    // 주문조회(검색) : QueryDSL
    public List<Order> searchOrderQueryDSL(OrderSearch orderSearch) {
        return orderRepository.findAllByQueryDSL(orderSearch);
    }

}
