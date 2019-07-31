package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDomain {

    private String articleId;
    private String categoryId;
    private String userId;
    private String title;
    private String content;
    private Date createTime;
    private Date lastEditTime;
    private String attachedFiles;
    private boolean isSoftDelete;

}
