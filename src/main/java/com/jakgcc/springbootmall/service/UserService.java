package com.jakgcc.springbootmall.service;

import com.jakgcc.springbootmall.model.User;
import com.jakgcc.springbootmall.rowmapper.dto.UserLoginRequest;
import com.jakgcc.springbootmall.rowmapper.dto.UserRegisterRequest;

import java.io.IOException;

public interface UserService {
    public Integer register(UserRegisterRequest userRegisterRequest) throws IOException;
    public User getUserById(Integer userId) throws IOException;

    User login(UserLoginRequest userLoginRequest) throws IOException;
}
