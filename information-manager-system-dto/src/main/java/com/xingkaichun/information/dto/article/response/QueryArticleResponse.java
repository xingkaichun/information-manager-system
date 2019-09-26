package com.xingkaichun.information.dto.article.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.base.PageInformation;
import lombok.Data;

@Data
public class QueryArticleResponse {

    @JsonProperty("ArticleDTOPageInformation")
    PageInformation<ArticleDTO> articleDTOPageInformation;

    public QueryArticleResponse() {
    }

    public QueryArticleResponse(PageInformation<ArticleDTO> articleDTOPageInformation) {
        this.articleDTOPageInformation = articleDTOPageInformation;
    }
}
