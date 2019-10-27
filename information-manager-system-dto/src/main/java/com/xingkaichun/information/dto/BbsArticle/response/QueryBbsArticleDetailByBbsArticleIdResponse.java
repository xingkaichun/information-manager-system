package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForDetailsPage;
import lombok.Data;

@Data
public class QueryBbsArticleDetailByBbsArticleIdResponse {

    @JsonProperty("BbsArticleDTO")
    private BbsArticleDTOForDetailsPage bbsArticleDTO;
}
