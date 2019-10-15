package com.xingkaichun.information.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PageCondition {

    @JsonProperty("PageNum")
    private int pageNum;
    @JsonProperty("PageSize")
    private int pageSize;

    //拼装sql时使用，它的值自动生成
    private int from;

    public PageCondition() {
    }

    public PageCondition(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getFrom() {
        return (pageNum-1)*pageSize;
    }
}
