package com.example.springjpa.api;

import com.example.springjpa.api.dto.order.ResponseOrderSelectDto;
import com.example.springjpa.domain.Order;
import com.example.springjpa.domain.OrderSearch;
import com.example.springjpa.global.result.CommonResult;
import com.example.springjpa.repository.OrderRepository;
import com.example.springjpa.repository.order.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne) 관계 최적화
 * Order
 * - Order -> Member    = N : 1
 * - Order -> Delivery  = 1 : 1
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * 간단한 주문조회 API V1 : 엔티티 직접 노출
     */
    @GetMapping("/api/v1/simple-orders")
    public CommonResult simpleOrdersV1() {
        log.info("[GET] /api/v1/simple-orders  =>  get orders version1");
        List<Order> orders = orderRepository.findAllByJPQL(new OrderSearch());
        for (Order order : orders) {
            // Lazy 강제 초기화
            order.getMember().getName();
            order.getDelivery().getStatus();
            /*List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.getCount();
            }*/
        }
        return new CommonResult<>(orders);
    }

    /**
     * 간단한 주문조회 API V2 : 엔티티를 DTO로 변환
     */
    @GetMapping("/api/v2/simple-orders")
    public CommonResult simpleOrdersV2() {
        log.info("[GET] /api/v2/simple-orders  =>  get orders version2");
        List<Order> orders = orderRepository.findAllByJPQL(new OrderSearch());

        List<ResponseOrderSelectDto> result = orders.stream()
                                                    .map(o -> ResponseOrderSelectDto.builder()
                                                                                            .orderId(o.getId())
                                                                                            .name(o.getMember().getName())  // LAZY
                                                                                            .orderDate(o.getOrderDate())
                                                                                            .orderStatus(o.getStatus())
                                                                                            .address(o.getDelivery().getAddress()) // LAZY
                                                                                            .build()
                                                    ).collect(Collectors.toList());
        return new CommonResult<>(result);
    }

    /**
     * 간단한 주문조회 API V3 : 엔티티를 DTO로 변환 - 페치 조인 최적화
     */
    @GetMapping("/api/v3/simple-orders")
    public CommonResult simpleOrdersV3() {
        log.info("[GET] /api/v3/simple-orders  =>  get orders version3");
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        List<ResponseOrderSelectDto> result = orders.stream()
                                                    .map(o -> ResponseOrderSelectDto.builder()
                                                            .orderId(o.getId())
                                                            .name(o.getMember().getName())
                                                            .orderDate(o.getOrderDate())
                                                            .orderStatus(o.getStatus())
                                                            .address(o.getDelivery().getAddress())
                                                            .build()
                                                    ).collect(Collectors.toList());
        return new CommonResult<>(result);
    }

    /**
     * 간단한 주문조회 API V4 : JPA에서 DTO로 바로 조회
     */
    @GetMapping("/api/v4/simple-orders")
    public CommonResult simpleOrdersV4() {
        log.info("[GET] /api/v4/simple-orders  =>  get orders version4");
        return new CommonResult<>(orderSimpleQueryRepository.findAllOrderDtos());
    }


}
