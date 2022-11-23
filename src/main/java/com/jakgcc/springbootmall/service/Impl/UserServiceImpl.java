package com.jakgcc.springbootmall.service.Impl;

import com.jakgcc.springbootmall.dao.UserDao;
import com.jakgcc.springbootmall.model.User;
import com.jakgcc.springbootmall.rowmapper.dto.UserRegisterRequest;
import com.jakgcc.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
@Component
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserDao userDao;
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) throws IOException {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        //檢查註冊的email
        if(null!=user){
            log.warn("該email {} 已被註冊",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //創建帳號
        return userDao.register(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) throws IOException {
        return userDao.getUserById(userId);
    }
}
