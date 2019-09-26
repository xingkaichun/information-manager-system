package com.xingkaichun.information.dto.bookSection.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryBookSectionListBybookChapterIdRequest {
    @JsonProperty("BookChapterId")
    private String bookChapterId;
}
