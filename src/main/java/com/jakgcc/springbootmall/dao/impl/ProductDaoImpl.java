package com.jakgcc.springbootmall.dao.impl;

import com.jakgcc.springbootmall.constant.ProductCategory;
import com.jakgcc.springbootmall.dao.ProductDao;
import com.jakgcc.springbootmall.dto.ProductRequest;
import com.jakgcc.springbootmall.dto.ProductRequestParams;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) throws IOException {
        String sql = readFile("sql/getProductById.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) throws IOException {
        String sql = readFile("sql/createProduct.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) throws IOException {
        String sql = readFile("sql/updateProductById.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        Date now = new Date();
        map.put("lastModifiedDate", now);
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProductId(Integer productId) throws IOException {
        String sql = readFile("sql/deleteProductId.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public List<Product> getProducts(ProductRequestParams productRequestParams) throws IOException {
        String sql = readFile("sql/getProducts.sql");
        Map<String, Object> map = new HashMap<>();
        if (null != productRequestParams.getProductCategory().name()) {
            sql += " AND category=:category ";
            map.put("category", productRequestParams.getProductCategory().name());
        }
        if (null != productRequestParams.getSearch()) {
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productRequestParams.getSearch() + "%");
        }
        //ORDER BY 用字串併接
        sql += " ORDER BY " + productRequestParams.getOrderBy() + " " + productRequestParams.getSort();

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    //
    //讀resources
    private String readFile(String relFilePath) throws IOException {
        final URL url = Resources.getResource(relFilePath);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }


}
