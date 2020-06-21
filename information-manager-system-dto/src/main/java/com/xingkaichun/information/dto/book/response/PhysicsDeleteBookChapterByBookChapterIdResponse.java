package com.xingkaichun.information.dto.book.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import lombok.Data;

@Data
public class PhysicsDeleteBookChapterByBookChapterIdResponse {
    @JsonProperty("BookChapterDTO")
    private BookChapterDTO bookChapterDTO;
}
