package com.xingkaichun.information.dto.article.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.article.ArticleDTO;

import java.util.List;

public class QueryArticleResponse {

    @JsonProperty("ArticleDTOList")
    private List<ArticleDTO> articleDTOList;

    public List<ArticleDTO> getArticleDTOList() {
        return articleDTOList;
    }

    public void setArticleDTOList(List<ArticleDTO> articleDTOList) {
        this.articleDTOList = articleDTOList;
    }
}
