package com.example.customer.serviceImpl;

import com.example.common.dto.LoginDto;
import com.example.common.vo.LoginResponse;
import com.example.common.vo.Response;
import com.example.customer.service.UserService;
import com.example.customer.entity.UserModel;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements FallbackFactory<UserService> {


    @Override
    public UserService create(Throwable cause) {
        return new UserService() {
            @Override
            public List<UserModel> selectUserList() {
                System.out.println("------------------------------");
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public LoginResponse<UserModel> login(LoginDto loginDto) {
                throw new RuntimeException(cause.getMessage());
            }

            @Override
            public void importUser() {
            }


        };
    }

}
