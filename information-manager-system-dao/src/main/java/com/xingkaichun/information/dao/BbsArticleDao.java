package com.xingkaichun.information.dao;

import com.xingkaichun.information.model.BbsArticleDomain;

public interface BbsArticleDao {
    int addBbsArticle(BbsArticleDomain bbsArticleDomain);
    BbsArticleDomain queryBbsArticleByBbsArticleId(String bbsArticleId);
}
