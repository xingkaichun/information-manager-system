package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.request.AddBbsArticleRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleRequest;

import java.util.List;

public interface BbsArticleService {

    int addBbsArticle(AddBbsArticleRequest addBbsArticleRequest);

    List<BbsArticleDTO> queryBbsArticleByRand();

    List<BbsArticleDTO> queryBbsArticleByUserId(String userId);

    ServiceResult<BbsArticleDTO> queryBbsArticleDetailByBbsArticleId(String bbsArticleId);

    PageInformation<BbsArticleDTO> queryBbsArticle(QueryBbsArticleRequest request);
}
