package com.jakgcc.springbootmall.dao.impl;

import com.jakgcc.springbootmall.dao.OrderDao;
import com.jakgcc.springbootmall.dto.OrderRequestParams;
import com.jakgcc.springbootmall.dto.ProductRequestParams;
import com.jakgcc.springbootmall.model.Order;
import com.jakgcc.springbootmall.model.OrderItem;
import com.jakgcc.springbootmall.rowmapper.OrderItemRowMapper;
import com.jakgcc.springbootmall.rowmapper.OrderRowMapper;
import com.jakgcc.springbootmall.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, int totalAmount) throws IOException {
        String sql = Tools.readFile("sql/createOrder.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("total_amount", totalAmount);
        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        Integer orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) throws IOException {
        //使用batchUpdate一次性加入數據，效率更高
        String sql = Tools.readFile("sql/createOrderItems.sql");
        MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("order_id", orderId);
            mapSqlParameterSource.addValue("product_id", orderItem.getProductId());
            mapSqlParameterSource.addValue("quantity", orderItem.getQuantity());
            mapSqlParameterSource.addValue("amount", orderItem.getAmount());
            mapSqlParameterSources[i] = mapSqlParameterSource;

        }
        namedParameterJdbcTemplate.batchUpdate(sql, mapSqlParameterSources);

    }

    @Override
    public Order getOrderById(Integer orderId) throws IOException {
        String sql = Tools.readFile("sql/getOrderById.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if (orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) throws IOException {
        String sql = Tools.readFile("sql/getOrderItemByOrderId.sql");
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        return orderItemList;
    }

    @Override
    public List<Order> getOrders(OrderRequestParams orderRequestParams) throws IOException {
        String sql = Tools.readFile("sql/getOrders.sql");
        Map<String, Object> map = new HashMap<>();
        //查詢條件
        sql = addFilteringSql(orderRequestParams, sql, map);
        //排序
        sql += " ORDER BY created_date DESC ";
        //分頁
        sql += " LIMIT :limit OFFSET :offset ";
        map.put("limit",orderRequestParams.getLimit());
        map.put("offset",orderRequestParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
        return orderList;
    }

    @Override
    public Integer countOrder(OrderRequestParams orderRequestParams) throws IOException {
        String sql = Tools.readFile("sql/countOrder.sql");
        Map<String, Object> map = new HashMap<>();
        //查詢條件
        sql = addFilteringSql(orderRequestParams, sql, map);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return count;
    }

    private static String addFilteringSql(OrderRequestParams orderRequestParams, String sql, Map<String, Object> map) {
        if(null!=orderRequestParams.getUserId()){
            sql+=" AND user_id = :user_id ";
            map.put("user_id",orderRequestParams.getUserId());
        }
        return sql;
    }
}
