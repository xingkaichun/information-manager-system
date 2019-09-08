package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.request.AddBbsArticleRequest;

import java.util.List;

public interface BbsArticleService {

    int addBbsArticle(AddBbsArticleRequest addBbsArticleRequest);

    List<BbsArticleDTO> queryBbsArticleByRand();

    List<BbsArticleDTO> queryBbsArticleByUserId(String userId);

    BbsArticleDTO queryBbsArticleDetailByBbsArticleId(String bbsArticleId);
}
