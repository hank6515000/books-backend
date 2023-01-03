package com.example.booksbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_order_item
 */
@TableName(value ="t_order_item")
@Data
public class OrderItem implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "book")
    private Integer book;

    @TableField(exist = false)
    private Book bookItem;

    /**
     * 
     */
    @TableField(value = "buyCount")
    private Integer buycount;

    /**
     * 
     */
    @TableField(value = "orderBean")
    private Integer orderbean;

    /**
     * 
     */
    @TableField(value = "orderNo")
    private String orderno;

    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}