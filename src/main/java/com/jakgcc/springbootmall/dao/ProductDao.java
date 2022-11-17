package com.jakgcc.springbootmall.dao;

import com.jakgcc.springbootmall.model.Product;

import java.io.IOException;

public interface ProductDao {
    public Product getProductById(Integer productId) throws IOException;
}
