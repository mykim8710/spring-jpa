package com.example.springjpa.service;

import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.Member;
import com.example.springjpa.domain.Order;
import com.example.springjpa.domain.OrderStatus;
import com.example.springjpa.domain.item.BookItem;
import com.example.springjpa.domain.item.Item;
import com.example.springjpa.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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

        int count = 5;

        // when
        Long orderId = orderService.order(member.getId(), bookItem.getId(), count);

        // then
        Order getOrder = orderRepository.findOneById(orderId).get();

        // 상품 주문시 상태는 ORDER
        System.out.println("ORDER Status = " + getOrder.getStatus());
        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);

        // 주문한 상품 종류 수가 정확해야 한다.
        System.out.println("OrderItem count = " + getOrder.getOrderItems().size());
        Assertions.assertThat(getOrder.getOrderItems().size()).isEqualTo(1);

        // 주문 가격은 가격 * 수량이다.
        System.out.println("order Total Price = " + getOrder.getOrderTotalPrice());
        Assertions.assertThat(getOrder.getOrderTotalPrice()).isEqualTo(itemPrice * count);

        // 주문 수량만큼 재고가 줄어야 한다.
        System.out.println("item stock Quantity = " +bookItem.getStockQuantity());
        Assertions.assertThat(bookItem.getStockQuantity()).isEqualTo(itemStockQuantity - count);
    }

    @Test
    @DisplayName("상품주문_재고_예외_테스트")
    void 상품주문_재고_예외_테스트() throws Exception {
        // given


        // when


        // then


    }

    @Test
    @DisplayName("주문취소_테스트")
    void 주문취소_테스트() throws Exception {
        // given


        // when


        // then


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