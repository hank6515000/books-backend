package com.example.booksbackend.service;

import com.example.booksbackend.pojo.BookClass;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Lenovo
* @description 针对表【t_book_class】的数据库操作Service
* @createDate 2022-12-24 21:26:15
*/
public interface BookClassService extends IService<BookClass> {

    public void addBookClass(String bookClass);
}
