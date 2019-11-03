package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.favorite.request.AddFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;

import java.util.List;

public interface UserFavoriteService {

    void addUserFavorite(AddFavoriteRequest request);

    void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request);

    List<UserFavoriteDto> queryUserFavoriteList(QueryUserFavoriteListRequest request);
}
