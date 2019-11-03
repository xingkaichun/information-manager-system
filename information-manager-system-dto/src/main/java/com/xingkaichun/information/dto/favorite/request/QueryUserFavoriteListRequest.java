package com.xingkaichun.information.dto.favorite.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageCondition;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;
import lombok.Data;

@Data
public class QueryUserFavoriteListRequest extends UserFavoriteDto {
    @JsonProperty("PageCondition")
    private PageCondition pageCondition;
}
