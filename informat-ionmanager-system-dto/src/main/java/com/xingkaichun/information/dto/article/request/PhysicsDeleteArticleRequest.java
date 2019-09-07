package com.xingkaichun.information.dto.article.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhysicsDeleteArticleRequest {

    @JsonProperty("ArticleId")
    private String articleId;
}
