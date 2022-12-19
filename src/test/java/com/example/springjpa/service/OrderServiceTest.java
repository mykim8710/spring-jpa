package com.example.springjpa.service;

import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.Member;
import com.example.springjpa.domain.Order;
import com.example.springjpa.domain.OrderStatus;
import com.example.springjpa.domain.item.BookItem;
import com.example.springjpa.domain.item.Item;
import com.example.springjpa.exception.NotEnoughStockException;
import com.example.springjpa.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @PersistenceContext EntityManager em; // JPA 스펙에서 제공하는 기능, 영속성 컨텍스트를 주입하는 표준 애노테이션

    @Test
    @DisplayName("상품주문_테스트")
    @Transactional
    void 상품주문_테스트() throws Exception {
        // given
        Member member = createMember();

        String itemName = "jpaBook";
        int itemPrice = 10000;
        int itemStockQuantity = 100;
        Item bookItem = createBook(itemName, itemPrice, itemStockQuantity);

        int orderCount = 5;

        // when
        Long orderId = orderService.order(member.getId(), bookItem.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOneById(orderId).get();

        // 상품 주문시 상태는 ORDER
        System.out.println("ORDER Status = " + getOrder.getStatus());
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);

        // 주문한 상품 종류 수가 정확해야 한다.
        System.out.println("OrderItem orderCount = " + getOrder.getOrderItems().size());
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);

        // 주문 가격은 가격 * 수량이다.
        System.out.println("order Total Price = " + getOrder.getOrderTotalPrice());
        assertThat(getOrder.getOrderTotalPrice()).isEqualTo(itemPrice * orderCount);

        // 주문 수량만큼 재고가 줄어야 한다.
        System.out.println("item stock Quantity = " +bookItem.getStockQuantity());
        assertThat(bookItem.getStockQuantity()).isEqualTo(itemStockQuantity - orderCount);
    }

    @Test
    @DisplayName("상품주문_재고_예외_테스트 : NotEnoughStockException 예외가 발생해야 한다.")
    @Transactional
    void 상품주문_재고_예외_테스트() throws Exception {
        // given
        Member member = createMember();
        String itemName = "jpaBook";
        int itemPrice = 10000;
        int itemStockQuantity = 10;
        Item bookItem = createBook(itemName, itemPrice, itemStockQuantity);

        // when & then
        assertThrows(NotEnoughStockException.class, () ->{
            int orderCount = 11;
            orderService.order(member.getId(), bookItem.getId(), orderCount);
        });
    }

    @Test
    @DisplayName("주문취소_테스트 : 주문을 취소하면 그만큼 재고가 증가해야 한다.")
    @Transactional
    void 주문취소_테스트() throws Exception {
        // given
        Member member = createMember();

        String itemName = "jpaBook";
        int itemPrice = 10000;
        int itemStockQuantity = 100;
        Item bookItem = createBook(itemName, itemPrice, itemStockQuantity);

        int orderCount = 5;
        Long orderId = orderService.order(member.getId(), bookItem.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOneById(orderId).get();

        // 상품 주문시 상태는 CANCEL
        System.out.println("ORDER Status = " + getOrder.getStatus());
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);

        // 주문 수량만큼 재고가 줄어야 한다.
        System.out.println("item stock Quantity = " +bookItem.getStockQuantity());
        assertThat(bookItem.getStockQuantity()).isEqualTo(itemStockQuantity);
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("name");
        member.setAddress(new Address("seoul", "gangnam", "100"));
        em.persist(member);
        return member;
    }
    private BookItem createBook(String name, int price, int stockQuantity) {
        BookItem book = new BookItem();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}