package com.jakgcc.springbootmall.service;

import com.jakgcc.springbootmall.rowmapper.dto.CreateOrderRequest;

import java.io.IOException;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) throws IOException;
}
