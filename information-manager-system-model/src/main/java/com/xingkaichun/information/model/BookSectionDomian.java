package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookSectionDomian {
    private String bookId;
    private String bookChapterId;
    private String bookSectionId;
    private String bookSectionName;
    private String bookSectionContent;
    private int bookSectionOrder;

    private Date createTime;
    private Date lastEditTime;
    private boolean isSoftDelete;
}
