package com.xingkaichun.information.dao;

import com.xingkaichun.information.model.BbsArticleDomain;

import java.util.List;

public interface BbsArticleDao {
    int addBbsArticle(BbsArticleDomain bbsArticleDomain);
    BbsArticleDomain queryBbsArticleByBbsArticleId(String bbsArticleId);
    List<BbsArticleDomain> queryBbsArticleByRand();
    List<BbsArticleDomain> queryBbsArticleByUserId(String userId);
}
