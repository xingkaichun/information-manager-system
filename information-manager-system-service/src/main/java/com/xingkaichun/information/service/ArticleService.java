package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.PhysicsDeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.dto.article.request.UpdateArticleRequest;

import java.io.IOException;

public interface ArticleService {

    int addArticle(AddArticleRequest addArticleRequest);

    int physicsDeleteArticle(PhysicsDeleteArticleRequest physicsDeleteArticleRequest);

    PageInformation<ArticleDTO> queryArticle(QueryArticleRequest queryArticleRequest);

    int updateArticle(UpdateArticleRequest updateArticleRequest);

    boolean hasArticleInCategoryId(String categoryId);

    boolean isSoftDelete(String articleId);

    void createArticleHtml(QueryArticleRequest queryArticleRequest) throws IOException;
}
