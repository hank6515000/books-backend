package com.example.booksbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.booksbackend.pojo.Order;
import com.example.booksbackend.service.OrderService;
import com.example.booksbackend.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【t_order】的数据库操作Service实现
* @createDate 2022-12-25 22:00:54
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




