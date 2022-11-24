package com.jakgcc.springbootmall.dao;

import com.jakgcc.springbootmall.model.User;
import com.jakgcc.springbootmall.dto.UserRegisterRequest;

import java.io.IOException;

public interface UserDao {
    Integer register(UserRegisterRequest userRegisterRequest) throws IOException;
    User getUserById(Integer userId) throws IOException;

    User getUserByEmail(String email) throws IOException;
}
