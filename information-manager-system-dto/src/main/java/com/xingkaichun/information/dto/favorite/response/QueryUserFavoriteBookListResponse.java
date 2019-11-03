package com.xingkaichun.information.dto.favorite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.book.BookDTO;
import lombok.Data;

@Data
public class QueryUserFavoriteBookListResponse {
    @JsonProperty("BookDTOList")
    private PageInformation<BookDTO> bookDTOList;
}
