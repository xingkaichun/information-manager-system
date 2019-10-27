package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForShowListPage;
import lombok.Data;

@Data
public class QueryBbsArticleResponse {
    @JsonProperty("BbsArticleDTOPageInformation")
    PageInformation<BbsArticleDTOForShowListPage> bbsArticleDTOPageInformation;

    public QueryBbsArticleResponse() {
    }

    public QueryBbsArticleResponse(PageInformation<BbsArticleDTOForShowListPage> bbsArticleDTOPageInformation) {
        this.bbsArticleDTOPageInformation = bbsArticleDTOPageInformation;
    }
}
