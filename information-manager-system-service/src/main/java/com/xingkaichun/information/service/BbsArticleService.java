package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.request.AddBbsArticleRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleRequest;
import com.xingkaichun.information.dto.base.PageInformation;
import com.xingkaichun.information.dto.base.ServiceResult;

import java.util.List;

public interface BbsArticleService {

    int addBbsArticle(AddBbsArticleRequest addBbsArticleRequest);

    List<BbsArticleDTO> queryBbsArticleByRand();

    List<BbsArticleDTO> queryBbsArticleByUserId(String userId);

    ServiceResult<BbsArticleDTO> queryBbsArticleDetailByBbsArticleId(String bbsArticleId);

    PageInformation<BbsArticleDTO> queryBbsArticle(QueryBbsArticleRequest request);
}
