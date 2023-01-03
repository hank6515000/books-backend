package com.example.booksbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.booksbackend.pojo.Book;
import com.example.booksbackend.service.BookService;
import com.example.booksbackend.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【t_book】的数据库操作Service实现
* @createDate 2022-12-24 10:18:03
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{

    @Autowired(required = false)
    BookMapper bookMapper;

    @Override
    public Book getBookByBookName(String bookName) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookname",bookName);
        Book book = bookMapper.selectOne(queryWrapper);
        return book;
    }

}




