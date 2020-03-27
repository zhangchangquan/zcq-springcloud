package com.example.customer.controller;


import com.example.customer.service.UserService;
import com.example.customer.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "userList",method = RequestMethod.GET)
    public List<UserModel> selectUserList(){
        List<UserModel> users = userService.selectUserList();
        return users;
    }

}
