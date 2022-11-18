package com.jakgcc.springbootmall.service;

import com.jakgcc.springbootmall.dto.ProductRequest;
import com.jakgcc.springbootmall.model.Product;

import java.io.IOException;

public interface ProductService {
    public Product getProductById(Integer productId) throws IOException;

    public Integer createProduct(ProductRequest productRequest) throws IOException;
}
