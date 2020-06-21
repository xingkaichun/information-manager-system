package com.xingkaichun.information.dto.book.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import lombok.Data;

@Data
public class PhysicsDeleteBookSectionByBookSectionIdResponse {
    @JsonProperty("BookSectionDTO")
    private BookSectionDTO bookSectionDTO;
}
