package com.xingkaichun.information.dto.BbsArticle.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryBbsArticleDetailByBbsArticleIdRequest {
    @JsonProperty("BbsArticleId")
    private String bbsArticleId;
}
