package com.xingkaichun.information.dto.BbsArticle.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageCondition;
import lombok.Data;

@Data
public class QueryBbsArticleCommentByUserRequest {
    @JsonProperty("PageCondition")
    PageCondition pageCondition;
    @JsonProperty("UserId")
    private String userId;
}
