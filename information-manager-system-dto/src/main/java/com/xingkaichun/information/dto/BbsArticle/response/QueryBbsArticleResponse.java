package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import lombok.Data;

@Data
public class QueryBbsArticleResponse {
    @JsonProperty("BbsArticleDTOPageInformation")
    PageInformation<BbsArticleDTO> bbsArticleDTOPageInformation;

    public QueryBbsArticleResponse() {
    }

    public QueryBbsArticleResponse(PageInformation<BbsArticleDTO> bbsArticleDTOPageInformation) {
        this.bbsArticleDTOPageInformation = bbsArticleDTOPageInformation;
    }
}
