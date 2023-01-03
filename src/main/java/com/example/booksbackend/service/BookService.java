package com.example.booksbackend.service;

import com.example.booksbackend.pojo.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Lenovo
* @description 针对表【t_book】的数据库操作Service
* @createDate 2022-12-24 10:18:03
*/
public interface BookService extends IService<Book> {

    public Book getBookByBookName(String bookName);


}
