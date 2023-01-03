package com.example.booksbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.booksbackend.pojo.OrderItem;
import com.example.booksbackend.service.OrderItemService;
import com.example.booksbackend.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【t_order_item】的数据库操作Service实现
* @createDate 2022-12-25 22:04:15
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{

    @Autowired(required = false)
    OrderItemMapper orderItemMapper;

    public List<OrderItem> getOrderItem(String orderNo){
        QueryWrapper<OrderItem>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderNo",orderNo);
        List<OrderItem> orderItemList = orderItemMapper.selectList(queryWrapper);

        return orderItemList;
    }
}




