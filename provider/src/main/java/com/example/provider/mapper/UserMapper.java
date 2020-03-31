package com.example.provider.mapper;

import com.example.provider.entity.UserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserModel> selectUserList();

    UserModel findUser(String userName,String password);
}
