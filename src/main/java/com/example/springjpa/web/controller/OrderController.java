package com.example.springjpa.web.controller;

import com.example.springjpa.domain.Member;
import com.example.springjpa.domain.Order;
import com.example.springjpa.domain.OrderSearch;
import com.example.springjpa.domain.item.Item;
import com.example.springjpa.service.ItemService;
import com.example.springjpa.service.MemberService;
import com.example.springjpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/orders/create")
    public String orderCreateFormView(Model model) {
        log.info("[GET] /orders/create  =>  Order Create View");

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "orders/orderCreateForm";
    }

    @PostMapping("/orders/create")
    public String orderCreate(@RequestParam("memberId") Long memberId,
                              @RequestParam("itemId") Long itemId,
                              @RequestParam("count") int count) {
        log.info("[POST] /orders/create  =>  Order Create");
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderListView(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        log.info("[GET] /orders  =>  Order List View");
        log.info("orderSearch : {}", orderSearch);

        //List<Order> orders = orderService.searchOrder(orderSearch);       // jpql
        List<Order> orders = orderService.searchOrderQueryDSL(orderSearch); // QueryDSL

        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

    @PostMapping(value = "/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable Long orderId) {
        log.info("[POST] /orders/{}/cancel  =>  Order Cancel", orderId);
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}

