package com.xxx.securityjwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.securityjwt.model.dao.UserMapper;
import com.xxx.securityjwt.model.entity.User;
import com.xxx.securityjwt.model.vo.LoginUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user==null){
            throw new RuntimeException("用户名或密码错误");
        }
        //Todo 根据用户查询权限信息 添加到LoginUser中
        //此处用模拟的权限信息进行
        List<String> permissions = userMapper.selectPermsByUserId(user.getId());

        return new LoginUserDetails(user,permissions);
    }
}
