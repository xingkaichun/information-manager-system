package com.xingkaichun.information.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    //书籍作者
    private String bookAuthor;
    //书籍语言
    private String bookLanguage;
    //书籍版本
    private String bookVersion;
    //书籍翻译作者
    private String bookTranslateAuthor;
    //书籍出版社
    private String bookPublishingHouse;
    //国际标准书号[书籍唯一标识号]
    private String bookISBN;
}
