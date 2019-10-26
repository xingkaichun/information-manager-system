package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BookDao;
import com.xingkaichun.information.dao.UserFavoriteDao;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.bookFavorite.request.AddFavoriteBookRequest;
import com.xingkaichun.information.dto.bookFavorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.bookFavorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;
import com.xingkaichun.information.model.UserFavoriteDomain;
import com.xingkaichun.information.service.BookService;
import com.xingkaichun.information.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service(value = "userFavoriteService")
public class UserFavoriteServiceImpl implements UserFavoriteService {

    @Autowired
    private UserFavoriteDao userFavoriteDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public void addUserFavorite(AddFavoriteBookRequest request) {
        userFavoriteDao.addUserFavorite(request);
    }

    @Override
    public void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request) {
        userFavoriteDao.physicsDeleteUserFavorite(request);
    }

    @Override
    public List<UserFavoriteDto> queryUserFavoriteList(QueryUserFavoriteListRequest request) {
        List<UserFavoriteDomain> userFavoriteDomain = userFavoriteDao.queryUserFavoriteList(request);
        return classCast(userFavoriteDomain);
    }

    private List<UserFavoriteDto> classCast(List<UserFavoriteDomain> userFavoriteDomain) {
        if(userFavoriteDomain==null){return null;}
        List<UserFavoriteDto> userFavoriteDtoList = new ArrayList<>();
        for(UserFavoriteDomain domain:userFavoriteDomain){
            userFavoriteDtoList.add(classCast(domain));
        }
        return userFavoriteDtoList;
    }

    private UserFavoriteDto classCast(UserFavoriteDomain domain) {
        UserFavoriteDto dto = new UserFavoriteDto();
        dto.setFavoriteId(domain.getFavoriteId());
        dto.setUserId(domain.getUserId());
        dto.setFavoriteType(domain.getFavoriteType());
        return dto;
    }
}
