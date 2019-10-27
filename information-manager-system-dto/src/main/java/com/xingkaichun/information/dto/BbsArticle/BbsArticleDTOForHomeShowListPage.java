package com.xingkaichun.information.dto.BbsArticle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BbsArticleDTOForHomeShowListPage {

    @JsonProperty("BbsArticleId")
    private String bbsArticleId;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("CreateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
