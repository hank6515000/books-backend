package com.example.booksbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_contact
 */
@TableName(value ="t_contact")
@Data
public class Contact implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "email")
    private String email;

    /**
     * 
     */
    @TableField(value = "massage")
    private String massage;

    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}