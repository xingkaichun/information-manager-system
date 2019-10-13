package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.bookFavorite.request.AddFavoriteBookRequest;
import com.xingkaichun.information.dto.bookFavorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.bookFavorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.model.UserFavoriteDomain;

import java.util.List;

public interface UserFavoriteDao {
    void addUserFavorite(AddFavoriteBookRequest request);

    void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request);

    List<UserFavoriteDomain> queryUserFavoriteList(QueryUserFavoriteListRequest request);
}
