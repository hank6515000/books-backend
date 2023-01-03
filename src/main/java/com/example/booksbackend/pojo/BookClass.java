package com.example.booksbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_book_class
 */
@TableName(value ="t_book_class")
@Data
public class BookClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "bid", type = IdType.AUTO)
    private Integer bid;

    /**
     * 
     */
    @TableField(value = "book_class")
    private String bookClass;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}