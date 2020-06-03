package com.example.provider.dao;

import com.example.common.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
@Mapper
public interface UserInfoDao {

    UserInfo getByUserName(@Param("username") String username);
}
