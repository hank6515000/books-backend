package com.example.booksbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.booksbackend.pojo.Contact;
import com.example.booksbackend.service.ContactService;
import com.example.booksbackend.mapper.ContactMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【t_contact】的数据库操作Service实现
* @createDate 2022-12-25 23:06:08
*/
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact>
    implements ContactService{

}




