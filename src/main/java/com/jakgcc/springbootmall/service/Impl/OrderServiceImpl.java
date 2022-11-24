package com.jakgcc.springbootmall.service.Impl;

import com.jakgcc.springbootmall.dao.OrderDao;
import com.jakgcc.springbootmall.dao.ProductDao;
import com.jakgcc.springbootmall.model.OrderItem;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.rowmapper.dto.BuyItem;
import com.jakgcc.springbootmall.rowmapper.dto.CreateOrderRequest;
import com.jakgcc.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) throws IOException {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());
            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;
            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);

        }
        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);
        return orderId;
    }
}
