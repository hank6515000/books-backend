package com.example.booksbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.booksbackend.pojo.Contact;
import com.example.booksbackend.pojo.Msg;
import com.example.booksbackend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("searchContact")
    public Msg searchContact(@RequestParam(value = "pn",defaultValue = "1")Integer pn , @RequestParam("keyword")String keyword){
        Page<Contact>page = new Page<>(pn,5);
        QueryWrapper<Contact>queryWrapper =new QueryWrapper<>();
        queryWrapper.like("name",keyword).or().like("email",keyword).or().like("massage",keyword);
        Page<Contact> contactPage = contactService.page(page, queryWrapper);

        return Msg.success().add("contactPage",contactPage);
    }

    @GetMapping("/getContact")
    public Msg getContact(@RequestParam(value = "pn" , defaultValue = "1")Integer pn){
        Page<Contact> page = new Page<>(pn,5);
        Page<Contact> contactPage = contactService.page(page, null);

        return Msg.success().add("contactPage",contactPage);
    }

    @DeleteMapping("/delContact/{cid}")
    public Msg delMember(@PathVariable("cid")Integer cid){
        boolean remove = contactService.removeById(cid);
        if (remove){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
