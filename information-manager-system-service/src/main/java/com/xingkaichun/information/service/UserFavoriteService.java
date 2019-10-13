package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.bookFavorite.request.AddFavoriteBookRequest;
import com.xingkaichun.information.dto.bookFavorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.bookFavorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;

import java.util.List;

public interface UserFavoriteService {

    void addUserFavorite(AddFavoriteBookRequest request);

    void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request);

    List<UserFavoriteDto> queryUserFavoriteList(QueryUserFavoriteListRequest request);
}
