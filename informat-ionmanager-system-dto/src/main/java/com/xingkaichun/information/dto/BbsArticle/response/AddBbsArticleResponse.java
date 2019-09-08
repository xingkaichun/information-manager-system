package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import lombok.Data;

@Data
public class AddBbsArticleResponse {

    @JsonProperty("BbsArticleDTO")
    private BbsArticleDTO bbsArticleDTO;
}
