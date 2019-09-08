package com.xingkaichun.information.dto.bookSection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
    @JsonProperty("BookSectionContent")
    private String bookSectionContent;
    @JsonProperty("BookSectionOrder")
    private int bookSectionOrder;

    @JsonProperty("CreateTime")
    private Date createTime;
    @JsonProperty("LastEditTime")
    private Date lastEditTime;
    @JsonProperty("IsSoftDelete")
    private boolean isSoftDelete;
}
