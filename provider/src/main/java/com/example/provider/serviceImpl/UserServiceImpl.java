package com.example.provider.serviceImpl;

import com.example.provider.entity.UserModel;
import com.example.provider.mapper.UserMapper;
import com.example.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserModel> selectUserList() {
        List<UserModel> users = userMapper.selectUserList();

        return users;
    }
}
