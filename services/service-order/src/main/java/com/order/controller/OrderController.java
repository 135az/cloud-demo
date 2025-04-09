package com.order.controller;

import com.order.Order;
import com.order.properties.OrderProperties;
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

    @Autowired
    private OrderProperties orderProperties;

    @GetMapping("/getConfig")
    public String getConfig() {
        return "orderTimeout: " + orderProperties.getTimeout() +
                ", orderAutoConfirm: " + orderProperties.getAutoConfirm() +
                ", dbUrl: " + orderProperties.getDbUrl();
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId) {
        return orderService.createOrder(productId, userId);
    }

    @GetMapping("/writeDb")
    public String writeDb() {
        return "writeDb success...";
    }

    @GetMapping("/readDb")
    public String readDb() {
        log.info("readDb success...");
        return "readDb success...";
    }


}
