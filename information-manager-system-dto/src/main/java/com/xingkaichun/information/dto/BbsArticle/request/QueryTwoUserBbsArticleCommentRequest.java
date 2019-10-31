package com.xingkaichun.information.dto.BbsArticle.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryTwoUserBbsArticleCommentRequest {

    @JsonProperty("BbsArticleId")
    private String bbsArticleId;
    @JsonProperty("UserAId")
    private String userAId;
    @JsonProperty("UserBId")
    private String userBId;

}
