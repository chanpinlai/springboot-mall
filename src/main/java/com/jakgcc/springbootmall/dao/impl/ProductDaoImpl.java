package com.jakgcc.springbootmall.dao.impl;

import com.jakgcc.springbootmall.dao.ProductDao;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0);
        }else{
            return null;
        }
    }

    //讀resources
    private String readFile(String relFilePath) throws IOException {
        final URL url = Resources.getResource(relFilePath);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }


}
