package com.xingkaichun.information.dto.BbsArticle.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageCondition;
import lombok.Data;

@Data
public class QueryBbsArticleCommentByForBbsArticleCommentIdRequest {

    @JsonProperty("BbsArticleCommentId")
    private String bbsArticleCommentId;
    @JsonProperty("ForBbsArticleCommentId")
    private String forBbsArticleCommentId;
    @JsonProperty("PageCondition")
    private PageCondition pageCondition;
}
