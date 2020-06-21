package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookSectionDomian {
    private Integer id;
    private String bookId;
    private String bookChapterId;
    private String bookSectionId;
    private String bookSectionName;
    private String bookSectionDescription;
    private String bookSectionContent;
    private int bookSectionOrder;

    private Date createTime;
    private Date lastEditTime;
    private boolean isSoftDelete;

    private String seoUrl;
    private String seoTitle;
    private String seoKeywords;
    private String seoDescription;

    private Integer auditStatus;
}
