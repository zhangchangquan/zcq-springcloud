package com.example.provider.controller;

import com.example.provider.entity.UserModel;
import com.example.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user-center/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public List<UserModel> selectUserList(){
        List<UserModel> users = userService.selectUserList();
        return users;
    }

    @RequestMapping(value = "import",method = RequestMethod.GET)
    public void importUser(){
        userService.importUser();
    }
}
