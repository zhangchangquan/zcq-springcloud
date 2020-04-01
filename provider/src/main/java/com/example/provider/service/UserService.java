package com.example.provider.service;

import com.example.common.vo.LoginResponse;
import com.example.common.vo.Response;
import com.example.provider.entity.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> selectUserList();

    LoginResponse<UserModel> findUser(String userName, String password);

    void importUser();
}
