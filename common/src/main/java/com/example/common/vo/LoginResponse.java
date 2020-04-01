package com.example.common.vo;

import lombok.Data;

@Data
public class LoginResponse<T> extends Response {

    private String token;

    public LoginResponse(){
        super();
    }
}
