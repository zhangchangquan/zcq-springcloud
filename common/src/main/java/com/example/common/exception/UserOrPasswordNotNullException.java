package com.example.common.exception;


import lombok.Data;

@Data
public class UserOrPasswordNotNullException extends RuntimeException{

    private String code;

    private String message;

    public UserOrPasswordNotNullException(String message){
        this.code = "E0001";
        this.message = message;
    }

}
