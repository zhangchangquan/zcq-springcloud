package com.example.customer.controller;


import com.example.common.dto.LoginDto;
import com.example.common.vo.Response;
import com.example.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.json",method = RequestMethod.POST)
    public Response login(@RequestBody LoginDto loginDto){
        Response response = userService.login(loginDto);

        return response;
    }
}
