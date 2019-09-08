package com.xingkaichun.information.dto.bookChapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BookChapterDTO {
    @JsonProperty("BookId")
    private String bookId;
    @JsonProperty("BookChapterId")
    private String bookChapterId;
    @JsonProperty("BookChapterName")
    private String bookChapterName;
    @JsonProperty("BookChapterOrder")
    private int bookChapterOrder;

    @JsonProperty("CreateTime")
    private Date createTime;
    @JsonProperty("IsSoftDelete")
    private boolean isSoftDelete;
}
