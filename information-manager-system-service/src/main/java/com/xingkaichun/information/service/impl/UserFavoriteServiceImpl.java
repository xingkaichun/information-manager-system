package com.xingkaichun.information.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dao.BbsArticleDao;
import com.xingkaichun.information.dao.BookDao;
import com.xingkaichun.information.dao.UserFavoriteDao;
import com.xingkaichun.information.dto.favorite.FavoriteType;
import com.xingkaichun.information.dto.favorite.UserFavoriteBbsArticleDto;
import com.xingkaichun.information.dto.favorite.UserFavoriteBookDto;
import com.xingkaichun.information.dto.favorite.request.AddFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.model.BbsArticleDomain;
import com.xingkaichun.information.model.UserFavoriteDomain;
import com.xingkaichun.information.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "userFavoriteService")
public class UserFavoriteServiceImpl implements UserFavoriteService {

    @Autowired
    private UserFavoriteDao userFavoriteDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BbsArticleDao bbsArticleDao;

    @Override
    public void addUserFavorite(AddFavoriteRequest request) {

        UserFavoriteDomain queryUserFavorite = new UserFavoriteDomain();
        queryUserFavorite.setUserId(request.getUserId());
        queryUserFavorite.setFavoriteId(request.getFavoriteId());
        queryUserFavorite.setFavoriteType(request.getFavoriteType());
        int userFavoriteSize = userFavoriteDao.queryUserFavoriteSize(queryUserFavorite);
        if(userFavoriteSize==0){
            userFavoriteDao.addUserFavorite(request);
            if(FavoriteType.BBS_ARTICLE_SUPPORT.name().equals(request.getFavoriteType())){
                updateSupportOfBbsArticle(request.getFavoriteId());
            }
        }

    }

    /**
     * 更新帖子赞的数量
     */
    private void updateSupportOfBbsArticle(String bbsArticleId) {
        UserFavoriteDomain querySizeOfSupportBbsArticle = new UserFavoriteDomain();
        querySizeOfSupportBbsArticle.setFavoriteId(bbsArticleId);
        querySizeOfSupportBbsArticle.setFavoriteType(FavoriteType.BBS_ARTICLE_SUPPORT.name());
        int sizeOfSupportBbsArticle = userFavoriteDao.queryUserFavoriteSize(querySizeOfSupportBbsArticle);

        BbsArticleDomain bbsArticleDomain = new BbsArticleDomain();
        bbsArticleDomain.setBbsArticleId(bbsArticleId);
        bbsArticleDomain.setNumberOfSupport(sizeOfSupportBbsArticle);
        bbsArticleDao.updateBbsArticle(bbsArticleDomain);
    }

    @Override
    public void physicsDeleteUserFavorite(PhysicsDeleteUserFavoriteRequest request) {
        userFavoriteDao.physicsDeleteUserFavorite(request);
        if(FavoriteType.BBS_ARTICLE_SUPPORT.name().equals(request.getFavoriteType())){
            updateSupportOfBbsArticle(request.getFavoriteId());
        }
    }

    @Override
    public PageInformation<UserFavoriteBookDto> queryUserFavoriteBookList(QueryUserFavoriteListRequest request) {
        PageHelper.startPage(request.getPageCondition().getPageNum(),request.getPageCondition().getPageSize());
        Page<UserFavoriteBookDto> page = userFavoriteDao.queryUserFavoriteBookList(request);
        PageInformation<UserFavoriteBookDto> pagePageInformation = new PageInformation<>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getResult());
        return pagePageInformation;
    }

    @Override
    public PageInformation<UserFavoriteBbsArticleDto> queryUserFavoriteBbsArticleList(QueryUserFavoriteListRequest request) {
        PageHelper.startPage(request.getPageCondition().getPageNum(),request.getPageCondition().getPageSize());
        Page<UserFavoriteBbsArticleDto> page = userFavoriteDao.queryUserFavoriteBbsArticleList(request);
        PageInformation<UserFavoriteBbsArticleDto> pagePageInformation = new PageInformation<>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getResult());
        return pagePageInformation;
    }
}
