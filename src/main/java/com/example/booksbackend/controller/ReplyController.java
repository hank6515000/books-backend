package com.example.booksbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.booksbackend.pojo.Book;
import com.example.booksbackend.pojo.Msg;
import com.example.booksbackend.pojo.Reply;
import com.example.booksbackend.pojo.User;
import com.example.booksbackend.service.BookService;
import com.example.booksbackend.service.ReplyService;
import com.example.booksbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @GetMapping("/searchBookName")
    public Msg searchBookName(@RequestParam(value = "pn", defaultValue = "1")Integer pn , @RequestParam("keyword")String keyword){
        Page<Reply> page = new Page<>(pn,5);
        QueryWrapper<Book>queryWrapperBook = new QueryWrapper<>();
        queryWrapperBook.like("bookname",keyword);
        List<Book> list = bookService.list(queryWrapperBook);
        Page<Reply> replyPage = null;
        QueryWrapper<Reply>queryWrapperReply = new QueryWrapper<>();
        for (Book book : list){
            queryWrapperReply.or().like("bookId",book.getId());
           replyPage = replyService.page(page, queryWrapperReply);
        }
        if (replyPage != null){
            setBookAndUser(replyPage);
            return Msg.success().add("replyPage",replyPage);
        }else {
            return Msg.fail();
        }
    }

    @GetMapping("/searchReplyUser")
    public Msg searchReplyUser(@RequestParam(value = "pn", defaultValue = "1")Integer pn , @RequestParam("keyword")String keyword){
        Page<Reply> page = new Page<>(pn,5);
        QueryWrapper<User>queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.like("name",keyword);
        List<User> list = userService.list(queryWrapperUser);
        Page<Reply> replyPage = null;
        QueryWrapper<Reply>queryWrapperReply = new QueryWrapper<>();
        for (User user : list){
            queryWrapperReply.or().like("replyUser",user.getId());
            replyPage = replyService.page(page, queryWrapperReply);
        }
        if (replyPage != null){
            setBookAndUser(replyPage);
            return Msg.success().add("replyPage",replyPage);
        }else {
            return Msg.fail();
        }
    }

    @GetMapping("/searchReply")
    public Msg searchReply(@RequestParam(value = "pn", defaultValue = "1")Integer pn , @RequestParam("keyword")String keyword){
        Page<Reply> page = new Page<>(pn,5);
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("reply",keyword);
        Page<Reply> replyPage = replyService.page(page, queryWrapper);

        if (replyPage != null){
            setBookAndUser(replyPage);
            return Msg.success().add("replyPage",replyPage);
        }else {
            return Msg.fail();
        }
    }

    @GetMapping("/getReply")
    public Msg getReply(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        Page<Reply>page = new Page<>(pn,10);
        Page<Reply> replyPage = replyService.page(page, null);

        if (replyPage != null){
            setBookAndUser(replyPage);
            return Msg.success().add("replyPage",replyPage);
        }else {
            return Msg.fail();
        }
    }

    @DeleteMapping("/delReply/{rid}")
    public Msg delReply(@PathVariable("rid")Integer rid){
        boolean remove = replyService.removeById(rid);
        if (remove){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }


    public void setBookAndUser(Page<Reply> replyPage){
        for (Reply reply : replyPage.getRecords()){
            QueryWrapper<Book> queryWrapperBook = new QueryWrapper<>();
            queryWrapperBook.eq("id",reply.getBookid());
            Book book = bookService.getOne(queryWrapperBook);
            reply.setBook(book);

            QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
            queryWrapperUser.eq("id",reply.getReplyuser());
            User user = userService.getOne(queryWrapperUser);
            reply.setUser(user);
        }
    }
}
