package com.xingkaichun.information.dto.article.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.article.ArticleDTO;
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
