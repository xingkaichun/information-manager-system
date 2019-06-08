package com.xingkaichun.information.dto.article.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.base.PageInformation;

public class QueryArticleResponse {

    @JsonProperty("ArticleDTOPageInformation")
    PageInformation<ArticleDTO> articleDTOPageInformation;

    public QueryArticleResponse() {
    }

    public QueryArticleResponse(PageInformation<ArticleDTO> articleDTOPageInformation) {
        this.articleDTOPageInformation = articleDTOPageInformation;
    }

    public PageInformation<ArticleDTO> getArticleDTOPageInformation() {
        return articleDTOPageInformation;
    }

    public void setArticleDTOPageInformation(PageInformation<ArticleDTO> articleDTOPageInformation) {
        this.articleDTOPageInformation = articleDTOPageInformation;
    }
}
