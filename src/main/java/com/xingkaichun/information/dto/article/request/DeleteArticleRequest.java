package com.xingkaichun.information.dto.article.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteArticleRequest {

    @JsonProperty("ArticleId")
    private String articleId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
