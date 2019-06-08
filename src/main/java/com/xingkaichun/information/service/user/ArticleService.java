package com.xingkaichun.information.service.user;

import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.DeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.dto.article.request.UpdateArticleRequest;
import com.xingkaichun.information.dto.base.PageInformation;

public interface ArticleService {

    int addArticle(AddArticleRequest addArticleRequest);

    int deleteArticle(DeleteArticleRequest deleteArticleRequest);

    PageInformation<ArticleDTO> queryArticle(QueryArticleRequest queryArticleRequest);

    int updateArticle(UpdateArticleRequest updateArticleRequest);
}
