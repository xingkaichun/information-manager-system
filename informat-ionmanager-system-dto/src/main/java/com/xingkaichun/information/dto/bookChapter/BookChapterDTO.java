package com.xingkaichun.information.dto.bookChapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import lombok.Data;

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
    @JsonProperty("BookChapterOrder")
    private Integer bookChapterOrder;


    @JsonProperty("CreateTime")
    private Date createTime;
    @JsonProperty("LastEditTime")
    private Date lastEditTime;
    @JsonProperty("IsSoftDelete")
    private boolean isSoftDelete;


    @JsonProperty("BookSectionDTOList")
    private List<BookSectionDTO> bookSectionDTOList;
}
