package com.xingkaichun.information.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddBookResponse {
    @JsonProperty("BookDTO")
    private BookDTO bookDTO;
}
