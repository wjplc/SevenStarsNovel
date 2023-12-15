package com.lyn.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjp
 * @since 2023/12/09
 */
@Data
@TableName("book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 小说名
     */
    @TableField("name")
    private String name;

    /**
     * 小说封面地址
     */
    @TableField("pic_url")
    private String picUrl;

    /**
     * 作家id
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 作家名
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 小说简介
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 小说评分
     */
    @TableField("score")
    private BigDecimal score;

    /**
     * 小说频道id
     */
    @TableField("channel_id")
    private Long channelId;

    /**
     * 小说类别id
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 小说类别名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 小说连载状态：0-连载中，1-已完结
     */
    @TableField("status")
    private Integer status;

    /**
     * 最新章节名
     */
    @TableField("last_chapter_number")
    private Integer lastChapterNumber;

    /**
     * 最新章节更新时间
     */
    @TableField("last_chapter_update_time")
    private LocalDateTime lastChapterUpdateTime;

    /**
     * 小说总字数
     */
    @TableField("word_count")
    private Integer wordCount;

    /**
     * 小说点击量
     */
    @TableField("visit_count")
    private Long visitCount;

    /**
     * 小说评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 小说是否会员专属：0-不是，1-是
     */
    @TableField("is_vip")
    private Boolean vip;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最近更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
