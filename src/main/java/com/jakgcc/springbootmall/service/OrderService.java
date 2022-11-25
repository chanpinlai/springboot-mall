package com.jakgcc.springbootmall.service;

import com.jakgcc.springbootmall.dto.OrderRequestParams;
import com.jakgcc.springbootmall.model.Order;
import com.jakgcc.springbootmall.dto.CreateOrderRequest;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) throws IOException;

    Order getOrderById(Integer orderId) throws IOException;

    List<Order> getOrders(OrderRequestParams orderRequestParams) throws IOException;

    Integer countOrder(OrderRequestParams orderRequestParams) throws IOException;
}
