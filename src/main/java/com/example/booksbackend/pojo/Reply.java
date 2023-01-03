package com.example.booksbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_reply
 */
@TableName(value ="t_reply")
@Data
public class Reply implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "bookId")
    private Integer bookid;

    @TableField(exist = false)
    private Book book;
    /**
     * 
     */
    @TableField(value = "replyUser")
    private Integer replyuser;

    @TableField(exist = false)
    private User user;
    /**
     * 
     */
    @TableField(value = "starNum")
    private Integer starnum;

    /**
     * 
     */
    @TableField(value = "reply")
    private String reply;

    /**
     * 
     */
    @TableField(value = "replyDate")
    private Date replydate;

    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}