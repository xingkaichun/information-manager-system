package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleRequest;
import com.xingkaichun.information.model.BbsArticleDomain;

import java.util.List;

public interface BbsArticleDao {
    int addBbsArticle(BbsArticleDomain bbsArticleDomain);
    BbsArticleDomain queryBbsArticleByBbsArticleId(String bbsArticleId);
    List<BbsArticleDomain> queryBbsArticleByRand();
    List<BbsArticleDomain> queryBbsArticleByUserId(String userId);

    List<BbsArticleDomain> queryBbsArticle(QueryBbsArticleRequest request);
    int queryBbsArticleCount(QueryBbsArticleRequest request);
}
