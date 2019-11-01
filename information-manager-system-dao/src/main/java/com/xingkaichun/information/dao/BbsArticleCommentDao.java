package com.xingkaichun.information.dao;

import com.github.pagehelper.Page;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleCommentByBbsArticleIdRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleCommentByForBbsArticleCommentIdRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleCommentByUserRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryTwoUserBbsArticleCommentRequest;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForBbsShowList;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForHomeShowList;
import com.xingkaichun.information.model.BbsArticleCommentDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BbsArticleCommentDao {
    int addBbsArticleComment(BbsArticleCommentDomain bbsArticleCommentDomain);
    BbsArticleCommentDomain querybbsArticleCommentBybbsArticleCommentId(String bbsArticleCommentId);
    List<BbsArticleCommentDomain> queryAllBbsArticleCommentBybbsArticleId(@Param("bbsArticleId") String bbsArticleId);
    int queryNumberOfComment(@Param("bbsArticleId") String bbsArticleId);
    Page<BbsArticleCommentDTOForHomeShowList> queryBbsArticleCommentByUser(QueryBbsArticleCommentByUserRequest request);

    Page<BbsArticleCommentDTOForBbsShowList> queryBbsArticleCommentByBbsArticleId(QueryBbsArticleCommentByBbsArticleIdRequest request);
    Page<BbsArticleCommentDTOForBbsShowList> queryBbsArticleCommentByForBbsArticleCommentId(QueryBbsArticleCommentByForBbsArticleCommentIdRequest request);

    List<BbsArticleCommentDTOForBbsShowList> queryTwoUserBbsArticleComment(QueryTwoUserBbsArticleCommentRequest request);
}
