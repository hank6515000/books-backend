package com.example.booksbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.booksbackend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 本地登入用戶Service
 */
@Service("userDetailService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String password = new BCryptPasswordEncoder().encode("12345");
        //設定權限
        List<GrantedAuthority> auths =  AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        return new org.springframework.security.core.userdetails.User("admin",password,auths);
    }

}
