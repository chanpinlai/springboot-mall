package com.jakgcc.springbootmall.dao;

import com.jakgcc.springbootmall.model.OrderItem;

import java.io.IOException;
import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, int totalAmount) throws IOException;

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList) throws IOException;
}
