package com.example.common.vo;

import lombok.Data;

@Data
public class Response {

    private String code;

    private Boolean success;

    private Object data;

    public Response(){
        super();
    }

    public Response(String code,Boolean success,Object data){
        this.code = code;
        this.success = success;
        this.data = data;
    }
}
