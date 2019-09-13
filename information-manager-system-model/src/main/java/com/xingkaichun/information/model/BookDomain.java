package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookDomain {
    private String bookId;
    private String authorId;
    private String bookName;
    private String bookDescription;

    private Date createTime;
    private Date lastEditTime;
    private boolean isSoftDelete;

    private String seoUrl;
    private String seoTitle;
    private String seoKeywords;
    private String seoDescription;

}
