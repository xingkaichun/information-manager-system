package com.xingkaichun.information.dto.BbsArticleComment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BbsArticleCommentDTOForBbsShowList {

    @JsonProperty("BbsArticleId")
    private String bbsArticleId;
    @JsonProperty("BbsArticleCommentId")
    private String bbsArticleCommentId;
    @JsonProperty("ParentBbsArticleCommentId")
    private String parentBbsArticleCommentId;
    @JsonProperty("Content")
    private String content;
    @JsonProperty("CreateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonProperty("FromUserId")
    private String fromUserId;
    @JsonProperty("FromUserName")
    private String fromUserName;

    @JsonProperty("ToUserId")
    private String toUserId;
    @JsonProperty("ToUserName")
    private String toUserName;
}
