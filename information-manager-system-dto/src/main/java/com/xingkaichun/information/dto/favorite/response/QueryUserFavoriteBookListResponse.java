package com.xingkaichun.information.dto.favorite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.book.BookDTO;
import lombok.Data;

import java.util.List;

@Data
public class QueryUserFavoriteBookListResponse {
    @JsonProperty("BookDTOList")
    private List<BookDTO> bookDTOList;
}
