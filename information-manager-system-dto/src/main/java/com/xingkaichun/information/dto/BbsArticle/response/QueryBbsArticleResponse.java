package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.base.PageInformation;
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
