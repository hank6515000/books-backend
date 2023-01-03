package com.example.booksbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.booksbackend.pojo.User;
import com.example.booksbackend.service.UserService;
import com.example.booksbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-12-28 23:00:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




