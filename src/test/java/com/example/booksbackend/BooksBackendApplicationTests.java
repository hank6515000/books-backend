package com.example.booksbackend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.booksbackend.pojo.Book;
import com.example.booksbackend.pojo.Order;
import com.example.booksbackend.pojo.OrderItem;
import com.example.booksbackend.pojo.User;
import com.example.booksbackend.service.BookService;
import com.example.booksbackend.service.OrderItemService;
import com.example.booksbackend.service.OrderService;
import com.example.booksbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BooksBackendApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    BookService bookService;


    @Test
    void contextLoads() {
        userService.removeById(28);

        List<User> userList = userService.list();

        System.out.println(userList);
    }
}
