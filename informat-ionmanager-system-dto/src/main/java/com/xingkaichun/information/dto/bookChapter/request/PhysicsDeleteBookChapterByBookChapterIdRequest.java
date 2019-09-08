package com.xingkaichun.information.dto.bookChapter.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhysicsDeleteBookChapterByBookChapterIdRequest {
    @JsonProperty("BookChapterId")
    private String bookChapterId;
}
