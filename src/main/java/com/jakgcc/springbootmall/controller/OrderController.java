package com.jakgcc.springbootmall.controller;

import com.jakgcc.springbootmall.dto.OrderRequestParams;
import com.jakgcc.springbootmall.model.Order;
import com.jakgcc.springbootmall.dto.CreateOrderRequest;
import com.jakgcc.springbootmall.service.OrderService;
import com.jakgcc.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/{userId}/orders")
    public ResponseEntity<Order> createOrder(
            @PathVariable Integer userId,
            @RequestBody @Valid CreateOrderRequest createOrderRequest
    ) throws IOException {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(order);

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) throws IOException {
        OrderRequestParams orderRequestParams = new OrderRequestParams();
        orderRequestParams.setUserId(userId);
        orderRequestParams.setLimit(limit);
        orderRequestParams.setOffset(offset);
        //取得order 總數
        Integer count = orderService.countOrder(orderRequestParams);

        //取得order list
        List<Order> orderList = orderService.getOrders(orderRequestParams);


        //分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResult(orderList);


        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

}
