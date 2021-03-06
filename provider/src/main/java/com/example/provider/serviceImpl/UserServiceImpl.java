package com.example.provider.serviceImpl;

import com.example.common.exception.UserOrPasswordNotNullException;
import com.example.common.util.Md5Util;
import com.example.common.vo.LoginResponse;
import com.example.provider.entity.UserModel;
import com.example.provider.mapper.UserMapper;
import com.example.provider.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<UserModel> selectUserList() {
        List<UserModel> users = userMapper.selectUserList();

        return users;
    }

    @Override
    public LoginResponse<UserModel> findUser(String userName, String password) {
        LoginResponse<UserModel> userModelResponse = new LoginResponse<>();
        try{
            if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
                throw new UserOrPasswordNotNullException("用户名或密码不能为空");
            }
            password = Md5Util.getMD5(password);
            UserModel userModel = userMapper.findUser(userName, password);
            userModelResponse.setResult(userModel);
        }catch (UserOrPasswordNotNullException e){
            userModelResponse.setCode(e.getCode());
            userModelResponse.setMessage(e.getMessage());
        }

        return userModelResponse;
    }

    @Override
    public void importUser() {
        String orderNo = UUID.randomUUID().toString();
        //2、将订单编号、用户ID保存到rabbitMQ中去
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("orderNo",orderNo);
        dataMap.put("userId",1);
        dataMap.put("seckillId",1);
        amqpTemplate.convertAndSend("queue-test",dataMap);

    }
}
