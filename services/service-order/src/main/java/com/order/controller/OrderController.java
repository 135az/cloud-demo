package com.order.controller;

import com.order.Order;
import com.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId) {
        return orderService.createOrder(productId, userId);
    }


}
