package com.jakgcc.springbootmall.dao;

import com.jakgcc.springbootmall.dto.ProductRequest;
import com.jakgcc.springbootmall.dto.ProductRequestParams;
import com.jakgcc.springbootmall.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductDao {
    public Product getProductById(Integer productId) throws IOException;
    public Integer createProduct(ProductRequest productRequest) throws IOException;

    void updateProduct(Integer productId,ProductRequest productRequest) throws IOException;

    void deleteProductId(Integer productId) throws IOException;

    List<Product> getProducts(ProductRequestParams productRequestParams) throws IOException;

    Integer countProduct(ProductRequestParams productRequestParams) throws IOException;

    void updateStock(Integer productId, Integer stock) throws IOException;
}
