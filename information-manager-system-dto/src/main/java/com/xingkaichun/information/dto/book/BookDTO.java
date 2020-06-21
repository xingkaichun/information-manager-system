package com.xingkaichun.information.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class BookDTO {
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("BookId")
    private String bookId;
    @JsonProperty("AuthorId")
    private String authorId;
    @JsonProperty("BookName")
    private String bookName;
    @JsonProperty("BookDescription")
    private String bookDescription;


    @JsonProperty("CreateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonProperty("LastEditTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTime;
    @JsonProperty("IsSoftDelete")
    private boolean isSoftDelete;


    @JsonProperty("SeoUrl")
    private String seoUrl;
    @JsonProperty("SeoTitle")
    private String seoTitle;
    @JsonProperty("SeoKeywords")
    private String seoKeywords;
    @JsonProperty("SeoDescription")
    private String seoDescription;


    @JsonProperty("BookChapterDTOList")
    private List<BookChapterDTO> bookChapterDTOList;

    @JsonProperty("AuditStatus")
    private Integer auditStatus;
}
