package com.xingkaichun.information.dto.bookChapter.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwapBookSectionOrderRequest {

    @JsonProperty("BookSectionAId")
    private String bookSectionAId;
    @JsonProperty("BookSectionBId")
    private String bookSectionBId;
}
