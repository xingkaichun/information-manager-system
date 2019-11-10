package com.xingkaichun.information.dto.bookChapter.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwapBookChapterOrderRequest {

    @JsonProperty("BookChapterAId")
    private String bookChapterAId;
    @JsonProperty("BookChapterBId")
    private String bookChapterBId;
}
