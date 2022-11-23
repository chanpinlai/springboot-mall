package com.jakgcc.springbootmall.dao.impl;

import com.jakgcc.springbootmall.dao.UserDao;
import com.jakgcc.springbootmall.model.User;
import com.jakgcc.springbootmall.rowmapper.UserRowMapper;
import com.jakgcc.springbootmall.rowmapper.dto.UserRegisterRequest;
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
public class UserDaoImpl implements UserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) throws IOException {
        String sql = Tools.readFile("sql/insertUser.sql");
        Map<String,Object> map = new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());
        Date date = new Date();
        map.put("created_date",date);
        map.put("last_modified_date",date);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer userId = keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserById(Integer userId) throws IOException {
        String sql = Tools.readFile("sql/getUserById.sql");
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }
}
