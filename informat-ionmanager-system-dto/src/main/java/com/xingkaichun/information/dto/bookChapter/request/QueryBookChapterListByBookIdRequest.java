package com.xingkaichun.information.dto.bookChapter.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryBookChapterListByBookIdRequest {
    @JsonProperty("BookId")
    private String bookId;
}
