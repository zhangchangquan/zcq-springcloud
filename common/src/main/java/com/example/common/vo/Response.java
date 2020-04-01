package com.example.common.vo;

import lombok.Data;

@Data
public class Response<T> {

    private String code = "200";

    private Boolean success = true;

    private String message;

    private T result;

    public Response(){
    }

    public void setCode(String code){
        this.code = code;
        this.success = false;
    }

}
