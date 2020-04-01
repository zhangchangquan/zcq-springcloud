package com.example.provider.controller;


import com.example.common.dto.LoginDto;
import com.example.common.vo.LoginResponse;
import com.example.common.vo.Response;
import com.example.provider.entity.UserModel;
import com.example.provider.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.json",method = RequestMethod.POST)
    public LoginResponse<UserModel> login(@RequestBody LoginDto loginDto){
        LoginResponse<UserModel> user = userService.findUser(loginDto.getUserName(), loginDto.getPassword());
        if(user.getSuccess() && user.getResult() != null){
            String jwtToken = jwtToken((UserModel) user.getResult());
            user.setToken(jwtToken);
        }
        return user;
    }

    private String jwtToken(UserModel userModel){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userName",userModel.getUserName());
        return Jwts.builder()
                .setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + 3000 * 1000))
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "jwttest").compact();
    }
}
