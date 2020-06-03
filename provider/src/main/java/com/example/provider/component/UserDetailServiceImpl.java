package com.example.provider.component;

import com.example.common.entity.UserInfo;
import com.example.provider.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null){
            username = "admin";
        }
        // 根据用户名查找用户
        UserInfo userInfo = userInfoDao.getByUserName(username);
        if(userInfo == null){
            throw  new  UsernameNotFoundException("用户不存在");
        }
        // 权限
        GrantedAuthority authority = new SimpleGrantedAuthority("admin");
        List<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(authority);

        UserDetails userDetails = new User(userInfo.getUsername(),passwordEncoder.encode(userInfo.getPassword()),authorities);
        // 返回用户信息，注意加密
        return userDetails;
    }
}

