package com.xingkaichun.information.dto.book.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhysicsDeleteBookByBookIdRequest {
    @JsonProperty("BookId")
    private String bookId;
}
