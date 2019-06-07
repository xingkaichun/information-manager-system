package com.xingkaichun.information.dto.article.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.article.ArticleDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QueryArticleRequest extends ArticleDTO {

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
}
