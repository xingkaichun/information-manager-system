package com.xingkaichun.information.dto.BbsArticle.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryBbsArticleByUserIdRequest {
    @JsonProperty("UserId")
    private String userId;
}
