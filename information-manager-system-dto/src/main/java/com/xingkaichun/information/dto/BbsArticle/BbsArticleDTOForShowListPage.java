package com.xingkaichun.information.dto.BbsArticle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BbsArticleDTOForShowListPage {

    @JsonProperty("BbsArticleId")
    private String bbsArticleId;

    @JsonProperty("UserId")
    private String userId;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("CreateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonProperty("LastEditTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTime;

    @JsonProperty("UserName")
    private String userName;

    @JsonProperty("NumberOfComment")
    private int numberOfComment;

    @JsonProperty("NumberOfSupport")
    private int numberOfSupport;
}
