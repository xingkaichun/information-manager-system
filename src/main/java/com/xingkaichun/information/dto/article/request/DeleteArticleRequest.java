package com.xingkaichun.information.dto.article.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteArticleRequest {

    @JsonProperty("ArticleId")
    private String articleId;
}
