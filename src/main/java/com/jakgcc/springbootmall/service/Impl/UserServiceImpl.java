package com.jakgcc.springbootmall.service.Impl;

import com.jakgcc.springbootmall.dao.UserDao;
import com.jakgcc.springbootmall.model.User;
import com.jakgcc.springbootmall.rowmapper.dto.UserRegisterRequest;
import com.jakgcc.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) throws IOException {
        return userDao.register(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) throws IOException {
        return userDao.getUserById(userId);
    }
}
