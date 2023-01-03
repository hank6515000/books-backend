package com.example.booksbackend.controller;

import com.example.booksbackend.pojo.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class IndexController {


    @GetMapping("/test")
    public String test(){
        return "test";
    }
    @GetMapping("/index")
    public String index(Principal principal, Model model){
        String name = principal.getName();
        System.out.println(name);
        model.addAttribute("name",name);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Principal principal){
        if (principal == null){
            return "login";
        }else {
            return "redirect:index";
        }

    }


}
