package com.example.booksbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.booksbackend.pojo.BookClass;
import com.example.booksbackend.service.BookClassService;
import com.example.booksbackend.mapper.BookClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【t_book_class】的数据库操作Service实现
* @createDate 2022-12-24 21:26:15
*/
@Service
public class BookClassServiceImpl extends ServiceImpl<BookClassMapper, BookClass>
    implements BookClassService{

    @Autowired(required = false)
    BookClassMapper bookClassMapper;

    @Override
    public void addBookClass(String bookClassName) {
        BookClass bookClass = new BookClass();
        bookClass.setBookClass(bookClassName);
        bookClassMapper.insert(bookClass);
    }
}




