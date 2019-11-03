package com.xingkaichun.information.dto.favorite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.favorite.UserFavoriteBbsArticleDto;
import lombok.Data;

@Data
public class QueryUserFavoriteBbsArticleListResponse {
    @JsonProperty("UserFavoriteBbsArticleDtoList")
    private PageInformation<UserFavoriteBbsArticleDto> userFavoriteBbsArticleDtoList;
}
