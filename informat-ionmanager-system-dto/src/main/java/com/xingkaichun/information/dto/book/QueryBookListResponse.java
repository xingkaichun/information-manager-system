package com.xingkaichun.information.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryBookListResponse {
    @JsonProperty("BookDTOList")
    private List<BookDTO> bookDTOList;
}
