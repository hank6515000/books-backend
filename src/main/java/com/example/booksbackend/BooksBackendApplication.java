package com.example.booksbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.booksbackend.mapper")
@SpringBootApplication
public class BooksBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksBackendApplication.class, args);
    }

}
