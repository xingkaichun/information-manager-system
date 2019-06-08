package com.xingkaichun.information.dto.article.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.base.PageCondition;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QueryArticleRequest extends ArticleDTO {

    @JsonProperty("PageCondition")
    PageCondition pageCondition;

    @JsonProperty("CreateTimeStart")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @JsonProperty("CreateTimeEnd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @JsonProperty("LastEditTimeStart")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTimeStart;

    @JsonProperty("LastEditTimeEnd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTimeEnd;


    public PageCondition getPageCondition() {
        return pageCondition;
    }

    public void setPageCondition(PageCondition pageCondition) {
        this.pageCondition = pageCondition;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Date getLastEditTimeStart() {
        return lastEditTimeStart;
    }

    public void setLastEditTimeStart(Date lastEditTimeStart) {
        this.lastEditTimeStart = lastEditTimeStart;
    }

    public Date getLastEditTimeEnd() {
        return lastEditTimeEnd;
    }

    public void setLastEditTimeEnd(Date lastEditTimeEnd) {
        this.lastEditTimeEnd = lastEditTimeEnd;
    }
}
