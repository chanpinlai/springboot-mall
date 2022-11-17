package com.jakgcc.springbootmall.service.Impl;

import com.jakgcc.springbootmall.dao.ProductDao;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) throws IOException {
        return productDao.getProductById(productId);
    }
}
