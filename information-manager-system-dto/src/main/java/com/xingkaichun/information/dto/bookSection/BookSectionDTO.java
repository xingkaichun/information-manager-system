package com.xingkaichun.information.dto.bookSection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BookSectionDTO {
    @JsonProperty("BookId")
    private String bookId;
    @JsonProperty("BookChapterId")
    private String bookChapterId;
    @JsonProperty("BookSectionId")
    private String bookSectionId;
    @JsonProperty("BookSectionName")
    private String bookSectionName;
    @JsonProperty("BookSectionDescription")
    private String bookSectionDescription;
    @JsonProperty("BookSectionContent")
    private String bookSectionContent;
    @JsonProperty("BookSectionOrder")
    private Integer bookSectionOrder;


    @JsonProperty("SeoUrl")
    private String seoUrl;
    @JsonProperty("SeoTitle")
    private String seoTitle;
    @JsonProperty("SeoKeywords")
    private String seoKeywords;
    @JsonProperty("SeoDescription")
    private String seoDescription;


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
}
