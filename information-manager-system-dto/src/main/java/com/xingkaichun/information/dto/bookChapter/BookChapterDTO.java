package com.xingkaichun.information.dto.bookChapter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class BookChapterDTO {
    @JsonProperty("BookId")
    private String bookId;
    @JsonProperty("BookChapterId")
    private String bookChapterId;
    @JsonProperty("BookChapterName")
    private String bookChapterName;
    @JsonProperty("BookChapterDescription")
    private String bookChapterDescription;
    @JsonProperty("BookChapterOrder")
    private Integer bookChapterOrder;


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


    @JsonProperty("BookSectionDTOList")
    private List<BookSectionDTO> bookSectionDTOList;

    @JsonProperty("AuditStatus")
    private Integer auditStatus;
}
