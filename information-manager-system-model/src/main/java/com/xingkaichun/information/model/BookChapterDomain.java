package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookChapterDomain {
    private String bookId;
    private String bookChapterId;
    private String bookChapterName;
    private int bookChapterOrder;

    private Date createTime;
    private Date lastEditTime;
    private boolean isSoftDelete;
}
