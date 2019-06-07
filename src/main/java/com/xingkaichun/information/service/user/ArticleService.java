package com.xingkaichun.information.service.user;

import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.DeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;

import java.util.List;

public interface ArticleService {

    int addArticle(AddArticleRequest addArticleRequest);

    int deleteArticle(DeleteArticleRequest deleteArticleRequest);

    List<ArticleDTO> queryArticle(QueryArticleRequest queryArticleRequest);
}
