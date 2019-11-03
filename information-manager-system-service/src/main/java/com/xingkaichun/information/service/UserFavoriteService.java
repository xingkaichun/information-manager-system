package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.favorite.request.AddFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;

import java.util.List;

public interface UserFavoriteService {

    void addUserFavorite(AddFavoriteRequest request);

    void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request);

    PageInformation<BookDTO> queryUserFavoriteList(QueryUserFavoriteListRequest request);
}
