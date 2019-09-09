package com.xingkaichun.information.dto.book.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.book.BookDTO;
import lombok.Data;

import java.util.List;

@Data
public class QueryBookListResponse {
    @JsonProperty("BookDTOList")
    private List<BookDTO> bookDTOList;
}
