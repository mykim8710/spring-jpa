package com.example.springjpa.web.controller;

import com.example.springjpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private OrderService orderService;

    @GetMapping("/orders/create")
    public String orderCreateFormView(Model model) {
        log.info("[GET] /orders/create  =>  Order Create View");

        return "orders/orderForm";
    }


    @GetMapping("/orders")
    public String orderListView() {
        log.info("[GET] /orders  =>  Order List View");



        return "orders/orderList";
    }

}

