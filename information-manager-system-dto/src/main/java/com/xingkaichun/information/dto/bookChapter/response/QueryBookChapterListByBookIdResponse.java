package com.xingkaichun.information.dto.bookChapter.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import lombok.Data;

import java.util.List;

@Data
public class QueryBookChapterListByBookIdResponse {

    @JsonProperty("BookChapterDTOList")
    private List<BookChapterDTO> bookChapterDTOList;
}
