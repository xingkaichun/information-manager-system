package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class BbsArticleCommentDomain {
    //文章ID
    private String bbsArticleId;
    //评论ID
    private String bbsArticleCommentId;
    //父评论ID
    private String parentBbsArticleCommentId;
    private String userId;
    private String toUserId;
    private String content;
    private Date createTime;
    private boolean isSoftDelete;

    private String forBbsArticleCommentId;

}
