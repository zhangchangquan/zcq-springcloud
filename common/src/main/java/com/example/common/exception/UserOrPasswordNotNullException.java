package com.example.common.exception;


import lombok.Data;

@Data
public class UserOrPasswordNotNullException extends RuntimeException{

    private String code;

    public UserOrPasswordNotNullException(String message){
        super(message);
        this.code = "E10001";

    }

}
