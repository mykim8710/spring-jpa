package com.example.springjpa.api;

import com.example.springjpa.api.dto.order.ResponseOrderSelectDto;
import com.example.springjpa.domain.Order;
import com.example.springjpa.domain.OrderItem;
import com.example.springjpa.domain.OrderSearch;
import com.example.springjpa.global.result.CommonResult;
import com.example.springjpa.repository.OrderRepository;
import com.example.springjpa.repository.order.OrderQueryRepository;
import com.example.springjpa.repository.order.dto.ResponseOrderFlatDto;
import com.example.springjpa.repository.order.dto.ResponseOrderItemQueryDto;
import com.example.springjpa.repository.order.dto.ResponseOrderQueryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * 주문조회 api V1 : 엔티티를 직접노출
     */
    @GetMapping("/api/v1/orders")
    public CommonResult ordersV1() {
        log.info("[GET] /api/v1/orders  => get Orders Version1");
        List<Order> orders = orderRepository.findAllByJPQL(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();        //Lazy 강제 초기화
            order.getDelivery().getAddress();   //Lazy 강제 초기화
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems
                    .stream()
                    .forEach(o -> o.getItem().getName()); //Lazy 강제초기화
        }
        return new CommonResult(orders);
    }

    /**
     * 주문조회 api V2 : 엔티티를 DTO로 변환
     */
    @GetMapping("/api/v2/orders")
    public CommonResult ordersV2() {
        log.info("[GET] /api/v2/orders  => get Orders Version2");
        List<Order> orders = orderRepository.findAllByJPQL(new OrderSearch());
        List<ResponseOrderSelectDto> responseOrderSelectDtos = orders
                                                                .stream()
                                                                .map(order -> new ResponseOrderSelectDto(order))
                                                                .collect(toList());

        return new CommonResult(responseOrderSelectDtos);
    }

    /**
     * 주문조회 api V3.1 : 엔티티를 DTO로 변환 - 페치 조인 최적화
     */
    @GetMapping("/api/v3-1/orders")
    public CommonResult ordersV3_1() {
        log.info("[GET] /api/v3-1/orders  => get Orders Version3-1");
        List<Order> orders = orderRepository.findAllWithMemberDeliveryOrderItemItem();
        List<ResponseOrderSelectDto> responseOrderSelectDtos = orders
                                                                .stream()
                                                                .map(order -> new ResponseOrderSelectDto(order))
                                                                .collect(toList());
        return new CommonResult(responseOrderSelectDtos);
    }

    /**
     * 주문조회 api V3.2 : 엔티티를 DTO로 변환 - 페이징과 한계돌파
     *  - ToOne 관계만 우선 모두 페치 조인으로 최적화
     *  - 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
     */
    @GetMapping("/api/v3-2/orders")
    public CommonResult ordersV3_2(@RequestParam(value = "offset", defaultValue = "0")   int offset,
                                   @RequestParam(value = "limit",  defaultValue = "100") int limit) {
        log.info("[GET] /api/v3-2/orders  => get Orders Version3-2");
        List<Order> orders = orderRepository.findAllWithMemberDeliveryPagination(offset, limit);
        List<ResponseOrderSelectDto> responseOrderSelectDtos = orders
                                                                .stream()
                                                                .map(order -> new ResponseOrderSelectDto(order))
                                                                .collect(toList());
        return new CommonResult(responseOrderSelectDtos);
    }

    /**
     * 주문조회 api V4 : JPA에서 DTO로 바로조회
     */
    @GetMapping("/api/v4/orders")
    public CommonResult ordersV4() {
        log.info("[GET] /api/v4/orders  => get Orders Version4");
        return new CommonResult(orderQueryRepository.findAllOrderQueryDtos());
    }

    /**
     * 주문조회 api V5 : JPA에서 DTO로 바로조회 -  컬렉션 조회 최적화
     */
    @GetMapping("/api/v5/orders")
    public CommonResult ordersV5() {
        log.info("[GET] /api/v5/orders  => get Orders Version5");
        return new CommonResult(orderQueryRepository.findAllOrderQueryDtosOptimization());
    }

    /**
     * 주문조회 api V6 : JPA에서 DTO로 바로조회, 플랫 데이터 최적화
     */
    @GetMapping("/api/v6/orders")
    public CommonResult ordersV6() {
        log.info("[GET] /api/v6/orders  => get Orders Version6");
        List<ResponseOrderFlatDto> responseOrderFlatDtos = orderQueryRepository.findAllOrderQueryDtosFlat();

        List<ResponseOrderQueryDto> collect = responseOrderFlatDtos.stream()
                .collect(groupingBy(o -> new ResponseOrderQueryDto(o.getOrderId(),
                                                                                    o.getName(),
                                                                                    o.getOrderDate(),
                                                                                    o.getOrderStatus(),
                                                                                    o.getAddress()),
                        mapping(o -> new ResponseOrderItemQueryDto(o.getOrderId(),
                                                                                     o.getItemName(),
                                                                                     o.getOrderPrice(),
                                                                                     o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new ResponseOrderQueryDto(e.getKey().getOrderId(),
                                                            e.getKey().getName(),
                                                            e.getKey().getOrderDate(),
                                                            e.getKey().getOrderStatus(),
                                                            e.getKey().getAddress(),
                                                            e.getValue())).collect(toList());
        return new CommonResult(collect);
    }

}
