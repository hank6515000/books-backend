package com.example.booksbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_user
 */
@TableName(value ="t_user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 
     */
    @TableField(value = "headImg_path")
    private String headimgPath;

    /**
     * 
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}