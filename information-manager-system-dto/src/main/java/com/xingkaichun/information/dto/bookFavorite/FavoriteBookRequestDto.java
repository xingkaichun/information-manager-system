package com.xingkaichun.information.dto.bookFavorite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FavoriteBookRequestDto {
    @JsonProperty("UserId")
    private String userId;
    @JsonProperty("FavoriteId")
    private String favoriteId;
    @JsonProperty("FavoriteType")
    private String favoriteType;
}
