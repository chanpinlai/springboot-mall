package com.jakgcc.springbootmall.service;

import com.jakgcc.springbootmall.dto.ProductRequest;
import com.jakgcc.springbootmall.dto.ProductRequestParams;
import com.jakgcc.springbootmall.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public Product getProductById(Integer productId) throws IOException;

    public Integer createProduct(ProductRequest productRequest) throws IOException;

    void updateProductById(Integer productId, ProductRequest productRequest) throws IOException;

    void deleteProductById(Integer productId) throws IOException;

    List<Product> getProducts(ProductRequestParams productRequestParams) throws IOException;

    Integer getCountProduct(ProductRequestParams productRequestParams) throws IOException;
}
