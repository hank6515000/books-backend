package com.example.booksbackend.service;

import com.example.booksbackend.pojo.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【t_order_item】的数据库操作Service
* @createDate 2022-12-25 22:04:15
*/
public interface OrderItemService extends IService<OrderItem> {

    public List<OrderItem> getOrderItem(String orderNo);
}
