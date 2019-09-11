package com.xingkaichun.information.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

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


    @JsonProperty("BookChapterDTOList")
    private List<BookChapterDTO> bookChapterDTOList;
}
