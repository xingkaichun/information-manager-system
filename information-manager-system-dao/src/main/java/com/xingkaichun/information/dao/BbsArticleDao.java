package com.xingkaichun.information.dao;

import com.github.pagehelper.Page;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForShowListPage;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleRequest;
import com.xingkaichun.information.model.BbsArticleDomain;

import java.util.List;

public interface BbsArticleDao {
    int addBbsArticle(BbsArticleDomain bbsArticleDomain);
    BbsArticleDomain queryBbsArticleByBbsArticleId(String bbsArticleId);
    List<BbsArticleDomain> queryBbsArticleByRand();
    List<BbsArticleDomain> queryBbsArticleByUserId(String userId);

    Page<BbsArticleDTOForShowListPage> queryBbsArticle(QueryBbsArticleRequest request);

    int updateBbsArticle(BbsArticleDomain bbsArticleDomain);
}
