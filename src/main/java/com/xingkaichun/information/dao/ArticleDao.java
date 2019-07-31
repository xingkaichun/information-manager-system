package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.article.request.PhysicsDeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.model.ArticleDomain;

import java.util.List;

public interface ArticleDao {
    int addArticle(ArticleDomain articleDomain);

    int physicsDeleteArticle(PhysicsDeleteArticleRequest physicsDeleteArticleRequest);

    List<ArticleDomain> queryArticle(QueryArticleRequest queryArticleRequest);

    int updateArticle(ArticleDomain articleDomain);

    int queryArticleTotalCount(QueryArticleRequest queryArticleRequest);
}
