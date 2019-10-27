package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForHomeShowListPage;
import lombok.Data;

@Data
public class QueryBbsArticleByUserIdResponse {

    @JsonProperty("BbsArticleDTOList")
    private PageInformation<BbsArticleDTOForHomeShowListPage> bbsArticleDTOList;
}
