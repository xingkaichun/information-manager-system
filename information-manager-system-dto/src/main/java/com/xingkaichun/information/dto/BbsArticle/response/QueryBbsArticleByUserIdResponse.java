package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import lombok.Data;

import java.util.List;

@Data
public class QueryBbsArticleByUserIdResponse {

    @JsonProperty("BbsArticleDTOList")
    private List<BbsArticleDTO> bbsArticleDTOList;
}
