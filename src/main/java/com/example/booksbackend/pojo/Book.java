package com.example.booksbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_book
 */
@TableName(value ="t_book")
@Data
public class Book implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "bookImg")
    private String bookimg;

    /**
     * 
     */
    @TableField(value = "bookName")
    private String bookname;

    /**
     * 
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 
     */
    @TableField(value = "author")
    private String author;

    /**
     * 
     */
    @TableField(value = "saleCount")
    private Integer salecount;

    /**
     * 
     */
    @TableField(value = "bookCount")
    private Integer bookcount;

    /**
     * 
     */
    @TableField(value = "bookClass")
    private Integer bookclass;

    @TableField(exist = false)
    private BookClass bookClassName;
    /**
     * 
     */
    @TableField(value = "publishingHouse")
    private String publishinghouse;

    /**
     * 
     */
    @TableField(value = "publicatioDate")
    private Date publicatiodate;

    /**
     * 
     */
    @TableField(value = "content")
    private String content;

    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}