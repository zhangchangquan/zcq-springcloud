package com.example.customer.controller;


import com.example.customer.entity.UserModel;
import com.example.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/test/")
public class UserController {

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "userList",method = RequestMethod.GET)
    public List<UserModel> selectUserList(){
        List<UserModel> users = userService.selectUserList();
        return users;
    }

    /**
     * user表导出excel
     */
    @RequestMapping(value = "import",method = RequestMethod.GET)
    public void importUser(){
        userService.importUser();
    }

    @RequestMapping(value = "testConfig",method = RequestMethod.GET)
    public String testConfig(){
        return this.name + this.age;
    }

}
