package com.xingkaichun.information.dao;

import com.xingkaichun.information.model.BbsArticleCommentDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BbsArticleCommentDao {
    int addBbsArticleComment(BbsArticleCommentDomain bbsArticleCommentDomain);
    BbsArticleCommentDomain querybbsArticleCommentBybbsArticleCommentId(String bbsArticleCommentId);
    List<BbsArticleCommentDomain> querybbsArticleCommentBybbsArticleId(@Param("bbsArticleId") String bbsArticleId);
    int queryNumberOfComment(@Param("bbsArticleId") String bbsArticleId);
}
