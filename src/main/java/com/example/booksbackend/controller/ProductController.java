package com.example.booksbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.booksbackend.pojo.Book;
import com.example.booksbackend.pojo.BookClass;
import com.example.booksbackend.pojo.Msg;
import com.example.booksbackend.service.BookClassService;
import com.example.booksbackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    BookService bookService;

    @Autowired
    BookClassService bookClassService;

    @GetMapping("/getProduct")
    public Msg getProduct(@RequestParam(value = "pn",defaultValue = "1")Integer pn){

        Page<Book>page = new Page<>(pn,5);
        Page<Book> bookPage = bookService.page(page, null);
        for (Book book : bookPage.getRecords()){
            QueryWrapper<BookClass> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("book_class").eq("bid",book.getBookclass());
            BookClass bookClass = bookClassService.getOne(queryWrapper);
            book.setBookClassName(bookClass);
        }
        List<BookClass> list = bookClassService.list();
        return Msg.success().add("bookPage",bookPage).add("bookClass",list);
    }

    @DeleteMapping("/delProduct/{bid}")
    public Msg delProduct(@PathVariable("bid")Integer bid){
        boolean remove = bookService.removeById(bid);

        FileSystemUtils.deleteRecursively(new File("C:\\Users\\Lenovo\\Desktop\\books-Backend\\target\\classes\\static\\asserts\\bookimgs\\book"+bid+".jpg"));

        if (remove){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }

    @GetMapping("/getBook/{bid}")
    public Msg getBook(@PathVariable("bid")Integer bid){
        Book book = bookService.getById(bid);
        List<BookClass> bookClasses = bookClassService.list();
        return Msg.success().add("book",book).add("bookClasses",bookClasses);
    }


    @PutMapping("/updateProductHasImg/{bid}")
    public Msg updateProductHasImg(@PathVariable("bid")Integer bid,@RequestParam("bookName")String bookName,
                             @RequestParam("price")Double price,@RequestParam("author")String author,@RequestParam("bookClass") Integer bookClass,
                             @RequestParam("bookCount")Integer bookCount,@RequestPart("updateImg")MultipartFile updateImg,
                             @RequestParam("publishingHouse")String publishingHouse,@RequestParam("publicationDate")String publicationDate,
                             @RequestParam("content")String content) throws ParseException, IOException {

        if (!updateImg.isEmpty()) {
            String fileName = updateImg.getOriginalFilename();
            int index = fileName.lastIndexOf(".");
            String suffixName;
            if (index > 0) {
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            } else {
                suffixName = ".jpg";
            }
            String bookImg = "book"+bid+suffixName;

            updateImg.transferTo(new File("C:\\bookimgs\\"+bookImg));

        }

        UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(publicationDate);

        updateWrapper.set("bookName", bookName).set("price", price).set("author", author)
                .set("bookCount", bookCount).set("bookClass", bookClass).set("publishingHouse", publishingHouse)
                .set("publicatioDate", date).set("content", content).eq("id", bid);

        bookService.update(updateWrapper);

            return Msg.success();

    }

    @PutMapping("/updateProduct/{bid}")
    public Msg updateProduct(@PathVariable("bid")Integer bid,@RequestParam("bookName")String bookName,
                                   @RequestParam("price")Double price,@RequestParam("author")String author,@RequestParam("bookClass") Integer bookClass,
                                   @RequestParam("bookCount")Integer bookCount,
                                   @RequestParam("publishingHouse")String publishingHouse,@RequestParam("publicationDate")String publicationDate,
                                   @RequestParam("content")String content) throws ParseException{

        UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(publicationDate);

        updateWrapper.set("bookName", bookName).set("price", price).set("author", author)
                .set("bookCount", bookCount).set("bookClass", bookClass).set("publishingHouse", publishingHouse)
                .set("publicatioDate", date).set("content", content).eq("id", bid);

        bookService.update(updateWrapper);

        return Msg.success();

    }


    @PostMapping(value = "/addProduct" , consumes = {"multipart/form-data"})
    public Msg addProduct(@RequestParam("bookName")String bookName, @RequestParam("price")Double price,
                          @RequestParam("author")String author,@RequestParam("bookClass") Integer bookClass,
                          @RequestParam("bookCount")Integer bookCount, @RequestParam("publishingHouse")String publishingHouse,
                          @RequestParam("publicationDate")String publicationDate, @RequestParam("content")String content,
                          @RequestPart("bookImg")MultipartFile bookImg) throws ParseException, IOException {
        List<Book> list = bookService.list();
        for (Book book:list){
            if (book.getBookname().equals(bookName)){
                return Msg.fail().add("msg","已有此書籍");
            }
        }
        Book book = new Book();
        book.setBookname(bookName);
        book.setPrice(price);
        book.setAuthor(author);
        book.setBookclass(bookClass);
        book.setSalecount(0);
        book.setBookcount(bookCount);
        book.setPublishinghouse(publishingHouse);
        //String轉換Date
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(publicationDate);
        book.setPublicatiodate(date);
        book.setContent(content);

        bookService.save(book);

        Book bookByBookName = bookService.getBookByBookName(book.getBookname());

        if (!bookImg.isEmpty()){
            String fileName = bookImg.getOriginalFilename();
            int index = fileName.lastIndexOf(".");
            String suffixName;
            if (index > 0){
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            }else {
                suffixName = ".jpg";
            }
            String newBookName = "book"+bookByBookName.getId()+suffixName;
            bookImg.transferTo(new File("C:\\bookimgs\\"+newBookName));
            UpdateWrapper<Book>updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("bookImg",newBookName).eq("id",bookByBookName.getId());
            bookService.update(updateWrapper);

            return Msg.success();
        }else {
            return Msg.fail().add("msg","圖片不可為空");
        }

    }

    @PostMapping("/addBookClass")
    public Msg addBookClass(@RequestParam("bookClass")String bookClass){
        List<BookClass> list = bookClassService.list();
        for (BookClass bookClassData: list){
            if (bookClassData.getBookClass().equals(bookClass)){
                return Msg.fail().add("msg","此類別已存在");
            }
        }

        bookClassService.addBookClass(bookClass);

        return Msg.success();
    }

    @GetMapping("/searchProduct")
    public Msg searchProduct(@RequestParam(value = "pn" , defaultValue = "1")Integer pn ,@RequestParam("keyword")String keyword){
        Page<Book> page = new Page<>(pn,5);
        QueryWrapper <Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("bookname",keyword).or().like("content",keyword).or().like("author",keyword);
        Page<Book> bookPage = bookService.page(page, queryWrapper);

        for (Book book : bookPage.getRecords()){
            QueryWrapper<BookClass> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.select("book_class").eq("bid",book.getBookclass());
            BookClass bookClass = bookClassService.getOne(queryWrapper1);
            book.setBookClassName(bookClass);
        }
        return Msg.success().add("bookPage",bookPage);
    }

    @GetMapping("/classSearch")
    public Msg classSearch(@RequestParam(value = "pn",defaultValue = "1")Integer pn , @RequestParam("keyword")Integer keyword){
        Page<Book>page = new Page<>(pn , 5);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookClass",keyword);
        Page<Book> bookPage = bookService.page(page, queryWrapper);

        for (Book book : bookPage.getRecords()){
            QueryWrapper<BookClass> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.select("book_class").eq("bid",book.getBookclass());
            BookClass bookClass = bookClassService.getOne(queryWrapper1);
            book.setBookClassName(bookClass);
        }

        return Msg.success().add("bookPage",bookPage);
    }


    @GetMapping("/getBookClass")
    public Msg getBookClass(){
        List<BookClass> list = bookClassService.list();

        return Msg.success().add("bookClass",list);
    }
}
