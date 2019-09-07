package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class BbsArticleDomain {

    private String bbsArticleId;
    private String userId;
    private String title;
    private String content;
    private Date createTime;
    private boolean isSoftDelete;
}
