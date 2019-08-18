package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.PhysicsDeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.dto.article.request.UpdateArticleRequest;
import com.xingkaichun.information.dto.base.PageInformation;
import com.xingkaichun.information.model.ArticleDomain;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;

public interface ArticleService {

    int addArticle(AddArticleRequest addArticleRequest);

    int physicsDeleteArticle(PhysicsDeleteArticleRequest physicsDeleteArticleRequest);

    PageInformation<ArticleDTO> queryArticle(QueryArticleRequest queryArticleRequest);

    int updateArticle(UpdateArticleRequest updateArticleRequest);

    boolean hasArticleInCategoryId(String categoryId);

    boolean isSoftDelete(String articleId);

    void createArticleHtml() throws IOException;
}
