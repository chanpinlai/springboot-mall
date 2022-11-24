package com.jakgcc.springbootmall.controller;

import com.jakgcc.springbootmall.rowmapper.dto.CreateOrderRequest;
import com.jakgcc.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/users")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/{userId}/orders")
    public ResponseEntity<Integer> createOrder(
            @PathVariable Integer userId,
            @RequestBody @Valid CreateOrderRequest createOrderRequest
            ) throws IOException {
        Integer orderId = orderService.createOrder(userId,createOrderRequest);
        return ResponseEntity.status(HttpStatus.OK).body(orderId);

    }

}
