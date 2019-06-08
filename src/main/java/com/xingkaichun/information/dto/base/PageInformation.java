package com.xingkaichun.information.dto.base;

import java.util.List;

public class PageInformation<T> {
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //总页数
    private int pages;
    //当前页数据
    private List<T> data;

    public PageInformation(int pageNum, int pageSize, int pages, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.data = data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPages() {
        return pages;
    }

    public List<T> getData() {
        return data;
    }
}
