package com.lyn.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjp
 * @since 2023/12/08
 */
@Data
@TableName("book_chapter")
public class BookChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 小说id
     */
    @TableField("book_id")
    private Long bookId;

    /**
     * 章节号
     */
    @TableField("number")
    private Integer number;

    /**
     * 章节名
     */
    @TableField("name")
    private String name;

    /**
     * 章节字数
     */
    @TableField("word_count")
    private Integer wordCount;

    /**
     * 是否收费：0-不，1-是
     */
    @TableField("is_vip")
    private Boolean vip;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
