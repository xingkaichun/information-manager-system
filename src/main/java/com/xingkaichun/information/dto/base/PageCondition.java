package com.xingkaichun.information.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageCondition {

    @JsonProperty("PageNum")
    private int pageNum;
    @JsonProperty("PageSize")
    private int pageSize;

    public PageCondition() {
    }

    public PageCondition(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
