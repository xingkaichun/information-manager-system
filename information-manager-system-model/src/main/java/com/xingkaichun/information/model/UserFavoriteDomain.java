package com.xingkaichun.information.model;

import lombok.Data;

@Data
public class UserFavoriteDomain {

    private String userId;
    private String favoriteId;
    private String favoriteType;

}
