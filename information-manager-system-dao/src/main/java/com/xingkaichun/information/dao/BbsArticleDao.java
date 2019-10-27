package com.xingkaichun.information.dao;

import com.github.pagehelper.Page;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForDetailsPage;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForHomeShowListPage;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForShowListPage;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleByUserIdRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleCommentByUserRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleRequest;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForHomeShowList;
import com.xingkaichun.information.model.BbsArticleDomain;

import java.util.List;

public interface BbsArticleDao {
    int addBbsArticle(BbsArticleDomain bbsArticleDomain);
    BbsArticleDomain queryBbsArticleByBbsArticleId(String bbsArticleId);
    BbsArticleDTOForDetailsPage queryBbsArticleByBbsArticleIdForDetailsPage(String bbsArticleId);

    List<BbsArticleDomain> queryBbsArticleByRand();
    Page<BbsArticleDTOForHomeShowListPage> queryBbsArticleByUserId(QueryBbsArticleByUserIdRequest request);

    Page<BbsArticleDTOForShowListPage> queryBbsArticle(QueryBbsArticleRequest request);

    int updateBbsArticle(BbsArticleDomain bbsArticleDomain);

}
