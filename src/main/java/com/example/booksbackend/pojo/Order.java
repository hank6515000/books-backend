package com.example.booksbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName t_order
 */
@TableName(value ="t_order")
@Data
public class Order implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "orderNo")
    private String orderno;

    /**
     * 
     */
    @TableField(value = "orderDate")
    private Date orderdate;

    /**
     * 
     */
    @TableField(value = "orderUser")
    private Integer orderuser;

    @TableField(exist = false)
    private User user;
    /**
     * 
     */
    @TableField(value = "orderMoney")
    private Double ordermoney;

    /**
     * 
     */
    @TableField(value = "orderStatus")
    private Integer orderstatus;

    @TableField(exist = false)
    private List<OrderItem> orderItemList;//一個訂單有多筆OrderItem

    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}