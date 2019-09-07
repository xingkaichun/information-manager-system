package com.xingkaichun.information.dto.base;

import lombok.Data;

import java.util.List;

@Data
public class PageInformation<T> {
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //总页数
    private int pages;
    //总条数
    private int totalCount;
    //当前页数据
    private List<T> data;


    public PageInformation(int pageNum, int pageSize, int totalCount, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;

        this.pages =  totalCount%pageSize == 0 ?totalCount/pageSize :totalCount/pageSize +1;
    }
}
