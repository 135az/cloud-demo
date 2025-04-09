package com.order.service;

import com.order.Order;

public interface OrderService {

    Order createOrder(Long productId, Long userId);
}
