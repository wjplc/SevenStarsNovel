package com.lyn.novel.result;

import lombok.Getter;

import java.util.List;

/**
 * 分页响应数据格式封装
 *
 * @author wjp
 * @date 2023/12/9
 */
@Getter
public class PageResult<T> {

    /**
     * 页码
     */
    private final long pageNum;

    /**
     * 每页大小
     */
    private final long pageSize;

    /**
     * 总记录数
     */
    private final long total;

    /**
     * 分页数据集
     */
    private final List<? extends T> list;

    /**
     * 该构造函数用于通用分页查询的场景 接收普通分页数据和普通集合
     */
    public PageResult(long pageNum, long pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public static <T> PageResult<T> of(long pageNum, long pageSize, long total, List<T> list) {
        return new PageResult<>(pageNum, pageSize, total, list);
    }


}
