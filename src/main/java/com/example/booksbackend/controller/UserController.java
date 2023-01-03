package com.example.booksbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.booksbackend.pojo.Msg;
import com.example.booksbackend.pojo.User;
import com.example.booksbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/searchMember")
    public Msg searchMember(@RequestParam(value = "pn", defaultValue = "1")Integer pn ,@RequestParam("keyword")String keyword){
        Page<User>page = new Page<>(pn,5);
        QueryWrapper<User>queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",keyword).or().like("name",keyword).or().like("phone",keyword);
        Page<User> userPage = userService.page(page, queryWrapper);

        return Msg.success().add("userPage",userPage);
    }

    @GetMapping("/getMember")
    public Msg getMember(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        Page<User>page = new Page<>(pn,10);
        Page<User> userPage = userService.page(page, null);

        return Msg.success().add("userPage",userPage).add("member","member");
    }

    @DeleteMapping("/delMember/{uid}")
    public Msg delMember(@PathVariable("uid")Integer uid){
        boolean remove = userService.removeById(uid);
        if (remove){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
