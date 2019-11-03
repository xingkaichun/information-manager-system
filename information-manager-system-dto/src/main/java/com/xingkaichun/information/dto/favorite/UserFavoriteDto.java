package com.xingkaichun.information.dto.favorite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserFavoriteDto {

    @JsonProperty("UserId")
    private String userId;
    @JsonProperty("FavoriteId")
    private String favoriteId;
    @JsonProperty("FavoriteType")
    private String favoriteType;

}
