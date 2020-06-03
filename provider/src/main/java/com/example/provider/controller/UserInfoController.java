package com.example.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserInfoController {

    @GetMapping("/user/web/{name}")
    public String web(@RequestParam("name") String name){
        return name;
    }

    @GetMapping("/user/android")
    public String android(){
        return  "hello android";
    }
}

