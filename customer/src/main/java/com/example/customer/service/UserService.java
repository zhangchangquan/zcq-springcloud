package com.example.customer.service;

import com.example.customer.serviceImpl.UserServiceImpl;
import com.example.customer.entity.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "provider",fallbackFactory = UserServiceImpl.class)
public interface UserService {

    @RequestMapping(value = "/user-center/list",method = RequestMethod.GET)
    List<UserModel> selectUserList();
}
