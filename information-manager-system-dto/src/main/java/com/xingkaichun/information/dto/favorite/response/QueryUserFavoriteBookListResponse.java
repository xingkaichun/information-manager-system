package com.xingkaichun.information.dto.favorite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.favorite.UserFavoriteBookDto;
import lombok.Data;

@Data
public class QueryUserFavoriteBookListResponse {
    @JsonProperty("UserFavoriteBookDtoList")
    private PageInformation<UserFavoriteBookDto> userFavoriteBookDtoList;
}
