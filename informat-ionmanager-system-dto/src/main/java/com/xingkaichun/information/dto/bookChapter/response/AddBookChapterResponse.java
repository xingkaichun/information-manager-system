package com.xingkaichun.information.dto.bookChapter.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import lombok.Data;

@Data
public class AddBookChapterResponse {
    @JsonProperty("BookChapterDTO")
    private BookChapterDTO bookChapterDTO;
}
