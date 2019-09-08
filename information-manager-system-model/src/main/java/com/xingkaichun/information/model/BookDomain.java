package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookDomain {
    private String bookId;
    private String authorId;
    private String bookName;

    private Date createTime;
    private boolean isSoftDelete;
}
