package com.xingkaichun.information.dto.book.request;

import lombok.Data;

@Data
public class QueryBookListRequest {
    private String authorId;
    private String seoUrl;
}
