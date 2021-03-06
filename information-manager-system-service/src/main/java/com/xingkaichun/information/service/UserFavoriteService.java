package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.favorite.UserFavoriteBbsArticleDto;
import com.xingkaichun.information.dto.favorite.UserFavoriteBookDto;
import com.xingkaichun.information.dto.favorite.request.AddFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;

import java.util.List;

public interface UserFavoriteService {

    void addUserFavorite(AddFavoriteRequest request);

    void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request);

    PageInformation<UserFavoriteBookDto> queryUserFavoriteBookList(QueryUserFavoriteListRequest request);

    PageInformation<UserFavoriteBbsArticleDto> queryUserFavoriteBbsArticleList(QueryUserFavoriteListRequest request);
}
