package com.xingkaichun.information.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dao.BookDao;
import com.xingkaichun.information.dao.UserFavoriteDao;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.favorite.request.AddFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;
import com.xingkaichun.information.model.UserFavoriteDomain;
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
    public void addUserFavorite(AddFavoriteRequest request) {
        userFavoriteDao.addUserFavorite(request);
    }

    @Override
    public void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request) {
        userFavoriteDao.physicsDeleteUserFavorite(request);
    }

    @Override
    public PageInformation<BookDTO> queryUserFavoriteList(QueryUserFavoriteListRequest request) {
        PageHelper.startPage(request.getPageCondition().getPageNum(),request.getPageCondition().getPageSize());
        Page<BookDTO> page = userFavoriteDao.queryUserFavoriteBookList(request);
        PageInformation<BookDTO> pagePageInformation = new PageInformation<>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getResult());
        return pagePageInformation;
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
