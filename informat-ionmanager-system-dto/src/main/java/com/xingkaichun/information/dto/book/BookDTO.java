package com.xingkaichun.information.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BookDTO {
    @JsonProperty("BookId")
    private String bookId;
    @JsonProperty("AuthorId")
    private String authorId;
    @JsonProperty("BookName")
    private String bookName;
    @JsonProperty("CreateTime")
    private Date createTime;
    @JsonProperty("IsSoftDelete")
    private boolean isSoftDelete;
}
