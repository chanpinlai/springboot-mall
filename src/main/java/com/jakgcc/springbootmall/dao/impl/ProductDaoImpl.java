package com.jakgcc.springbootmall.dao.impl;

import com.jakgcc.springbootmall.dao.ProductDao;
import com.jakgcc.springbootmall.dto.ProductRequest;
import com.jakgcc.springbootmall.dto.ProductRequestParams;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.rowmapper.ProductRowMapper;
import com.jakgcc.springbootmall.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        String sql = Tools.readFile("sql/getProductById.sql");
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
        String sql = Tools.readFile("sql/createProduct.sql");
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
        String sql = Tools.readFile("sql/updateProductById.sql");
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
        String sql = Tools.readFile("sql/deleteProductId.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public Integer countProduct(ProductRequestParams productRequestParams) throws IOException {
        String sql = Tools.readFile("sql/countProduct.sql");
        Map<String, Object> map = new HashMap<>();
        //查詢條件
        sql = addFilteringSql(productRequestParams, sql, map);
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

    }

    @Override
    public List<Product> getProducts(ProductRequestParams productRequestParams) throws IOException {
        String sql = Tools.readFile("sql/getProducts.sql");
        Map<String, Object> map = new HashMap<>();
        //查詢條件
        sql = addFilteringSql(productRequestParams, sql, map);
        //ORDER BY 用字串併接
        sql += " ORDER BY " + productRequestParams.getOrderBy() + " " + productRequestParams.getSort();
//分頁
        sql += " LIMIT :limit OFFSET :offset ";
        map.put("limit", productRequestParams.getLimit());
        map.put("offset", productRequestParams.getOffset());
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    private static String addFilteringSql(ProductRequestParams productRequestParams, String sql, Map<String, Object> map) {
        if (null != productRequestParams.getProductCategory()) {
            sql += " AND category=:category ";
            map.put("category", productRequestParams.getProductCategory().name());
        }
        if (null != productRequestParams.getSearch()) {
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productRequestParams.getSearch() + "%");
        }
        return sql;
    }

    @Override
    public void updateStock(Integer productId, Integer stock) throws IOException {
        String sql = Tools.readFile("sql/updateStock.sql");
        Map<String,Object> map = new HashMap<>();
        map.put("stock",stock);
        map.put("last_modified_date",new Date());
        map.put("product_id",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
