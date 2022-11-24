package com.jakgcc.springbootmall.service;

import com.jakgcc.springbootmall.model.Order;
import com.jakgcc.springbootmall.dto.CreateOrderRequest;

import java.io.IOException;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) throws IOException;

    Order getOrderById(Integer orderId) throws IOException;
}
