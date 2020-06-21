package com.xingkaichun.information.dto.book.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetSectionPageUrlByBookSectionIdRequest {

    @JsonProperty("BookSectionId")
    private String bookSectionId;
}
