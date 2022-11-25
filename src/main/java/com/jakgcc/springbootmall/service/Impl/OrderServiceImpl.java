package com.jakgcc.springbootmall.service.Impl;

import com.jakgcc.springbootmall.dao.OrderDao;
import com.jakgcc.springbootmall.dao.ProductDao;
import com.jakgcc.springbootmall.dao.UserDao;
import com.jakgcc.springbootmall.dto.BuyItem;
import com.jakgcc.springbootmall.dto.CreateOrderRequest;
import com.jakgcc.springbootmall.dto.OrderRequestParams;
import com.jakgcc.springbootmall.model.Order;
import com.jakgcc.springbootmall.model.OrderItem;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.model.User;
import com.jakgcc.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    UserDao userDao;
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) throws IOException {
        //查檢user是否存在
        User user = userDao.getUserById(userId);
        if (null == user) {
            log.warn("該userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());
            //檢查 product 是否存在
            if (null == product) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //庫存是否足夠
            if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //扣除商品庫存
            productDao.updateStock(buyItem.getProductId(), product.getStock() - buyItem.getQuantity());

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

    @Override
    public Order getOrderById(Integer orderId) throws IOException {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    public List<Order> getOrders(OrderRequestParams orderRequestParams) throws IOException {
        List<Order> orderList = orderDao.getOrders(orderRequestParams);
        List<OrderItem> orderItemList = null;
        for(Order order:orderList){
            orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    @Override
    public Integer countOrder(OrderRequestParams orderRequestParams) throws IOException {
        return orderDao.countOrder(orderRequestParams);
    }
}
