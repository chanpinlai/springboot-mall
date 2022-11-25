package com.jakgcc.springbootmall.dao;

import com.jakgcc.springbootmall.dto.OrderRequestParams;
import com.jakgcc.springbootmall.model.Order;
import com.jakgcc.springbootmall.model.OrderItem;

import java.io.IOException;
import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, int totalAmount) throws IOException;

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList) throws IOException;

    Order getOrderById(Integer orderId) throws IOException;

    List<OrderItem> getOrderItemByOrderId(Integer orderId) throws IOException;

    List<Order> getOrders(OrderRequestParams orderRequestParams) throws IOException;

    Integer countOrder(OrderRequestParams orderRequestParams) throws IOException;
}
