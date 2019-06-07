package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.DeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.model.ArticleDomain;

import java.util.List;

public interface ArticleDao {
    int addArticle(ArticleDomain articleDomain);

    int deleteArticle(DeleteArticleRequest deleteArticleRequest);

    List<ArticleDomain> queryArticle(QueryArticleRequest queryArticleRequest);

    int updateArticle(ArticleDomain classCast);
}
