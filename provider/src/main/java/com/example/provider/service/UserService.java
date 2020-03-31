package com.example.provider.service;

import com.example.provider.entity.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> selectUserList();

    UserModel findUser(String userName,String password);
}
