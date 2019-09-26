package com.xingkaichun.information.dto.bookSection.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import lombok.Data;

import java.util.List;

@Data
public class QueryBookSectionListBybookChapterIdResponse {
    @JsonProperty("BookSectionDTOList")
    private List<BookSectionDTO> bookSectionDTOList;
}
