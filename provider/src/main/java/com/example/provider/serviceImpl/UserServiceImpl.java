package com.example.provider.serviceImpl;

import com.example.common.exception.UserOrPasswordNotNullException;
import com.example.common.util.Md5Util;
import com.example.provider.entity.UserModel;
import com.example.provider.mapper.UserMapper;
import com.example.provider.service.UserService;
import org.apache.commons.lang.StringUtils;
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

    @Override
    public UserModel findUser(String userName, String password) {
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            throw new UserOrPasswordNotNullException("用户名不能为空");
        }
        password = Md5Util.getMD5(password);
        UserModel userModel = userMapper.findUser(userName, password);

        return userModel;
    }
}
