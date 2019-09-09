package com.xingkaichun.information.dto.bookSection.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import lombok.Data;

@Data
public class AddBookSectionResponse {
    @JsonProperty("BookSectionDTO")
    private BookSectionDTO bookSectionDTO;
}
