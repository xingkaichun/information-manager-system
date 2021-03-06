package com.xingkaichun.information.dto.book.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.book.BookDTO;
import lombok.Data;

@Data
public class UpdateBookResponse {
    @JsonProperty("BookDTO")
    private BookDTO bookDTO;
}
