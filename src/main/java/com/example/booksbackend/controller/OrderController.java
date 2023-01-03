package com.example.booksbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.booksbackend.pojo.*;
import com.example.booksbackend.service.BookService;
import com.example.booksbackend.service.OrderItemService;
import com.example.booksbackend.service.OrderService;
import com.example.booksbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    BookService bookService;

    @GetMapping("/searchOrderDate")
    public Msg searchOrderDate(@RequestParam(value = "pn",defaultValue = "1")Integer pn ,@RequestParam("keyword")String keyword) {
        Page<Order> page = new Page<>(pn, 5);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("orderDate",keyword);
        Page<Order> orderPage = orderService.page(page, queryWrapper);

        setUserAndOrderItem(orderPage);

        return Msg.success().add("orderPage", orderPage);
    }

    @GetMapping("/searchOrderNo")
    public Msg searchOrderNo(@RequestParam(value = "pn",defaultValue = "1")Integer pn ,@RequestParam("keyword")String keyword) {
        Page<Order> page = new Page<>(pn, 5);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("orderNo",keyword);
        Page<Order> orderPage = orderService.page(page, queryWrapper);

        setUserAndOrderItem(orderPage);

        return Msg.success().add("orderPage", orderPage);
    }

    @GetMapping("/searchOrder")
    public Msg searchOrder(@RequestParam(value = "pn",defaultValue = "1")Integer pn ,@RequestParam("keyword")String keyword) {
        Page<Order> page = new Page<>(pn, 5);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        List<User> userList = userService.list(queryWrapper);
        QueryWrapper<Order> queryWrapperOrder = new QueryWrapper<>();
        Page<Order> orderPage = null;
        for (User user : userList) {
            queryWrapperOrder.or().like("orderUser", user.getId());
            orderPage = orderService.page(page, queryWrapperOrder);
            setUserAndOrderItem(orderPage);
        }
            return Msg.success().add("orderPage", orderPage);
        }


    @GetMapping("/getOrder")
    public Msg getOrder(@RequestParam(value = "pn" , defaultValue = "1")Integer pn){
        Page<Order> page = new Page<>(pn,5);
        Page<Order>orderPage = orderService.page(page,null);
        setUserAndOrderItem(orderPage);
        return Msg.success().add("orderPage",orderPage);
    }

    @DeleteMapping("/delOrder/{oid}")
    public Msg delOrder(@PathVariable("oid")Integer oid){
       QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("id",oid);
        Order order = orderService.getOne(queryWrapper);
        QueryWrapper<OrderItem> queryWrapperItem = new QueryWrapper<>();
        queryWrapperItem.eq("orderNo",order.getOrderno());
        orderItemService.remove(queryWrapperItem);
        orderService.removeById(oid);
        return Msg.success();
    }



    public void setUserAndOrderItem(Page<Order>orderPage){
        for (Order order : orderPage.getRecords()) {
            //設置orderItem實例
            List<OrderItem> orderItemList = orderItemService.getOrderItem(order.getOrderno());
            //設置orderItem中book實例
            List<OrderItem> newOrderItemList = new ArrayList<>();
            for (OrderItem orderItem : orderItemList){
            Book book = bookService.getById(orderItem.getBook());
            orderItem.setBookItem(book);
            newOrderItemList.add(orderItem);
           }
            order.setOrderItemList(orderItemList);
            //設置user實例
            User user = userService.getById(order.getOrderuser());
            order.setUser(user);
        }
    }
}
