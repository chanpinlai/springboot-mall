package com.jakgcc.springbootmall.service.Impl;

import com.jakgcc.springbootmall.dao.ProductDao;
import com.jakgcc.springbootmall.dto.ProductRequest;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) throws IOException {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) throws IOException {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProductById(Integer productId, ProductRequest productRequest) throws IOException {
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) throws IOException {
        productDao.deleteProductId(productId);
    }

    @Override
    public List<Product> getProducts() throws IOException {
        return productDao.getProducts();
    }
}
