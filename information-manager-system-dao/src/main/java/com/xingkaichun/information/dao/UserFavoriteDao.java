package com.xingkaichun.information.dao;

import com.github.pagehelper.Page;
import com.xingkaichun.information.dto.favorite.UserFavoriteBbsArticleDto;
import com.xingkaichun.information.dto.favorite.UserFavoriteBookDto;
import com.xingkaichun.information.dto.favorite.request.AddFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.model.UserFavoriteDomain;

import java.util.List;

public interface UserFavoriteDao {
    void addUserFavorite(AddFavoriteRequest request);

    void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request);

    List<UserFavoriteDomain> queryUserFavorite(UserFavoriteDomain request);

    Page<UserFavoriteBookDto> queryUserFavoriteBookList(QueryUserFavoriteListRequest request);

    Page<UserFavoriteBbsArticleDto> queryUserFavoriteBbsArticleList(QueryUserFavoriteListRequest request);
}
