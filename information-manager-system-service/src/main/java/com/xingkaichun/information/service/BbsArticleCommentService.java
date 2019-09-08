package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.BbsArticleComment.request.AddBbsArticleCommentRequest;
import com.xingkaichun.information.dto.base.FreshServiceResult;

public interface BbsArticleCommentService {

    FreshServiceResult AddBbsArticleComment(AddBbsArticleCommentRequest addBbsArticleCommentRequest);
}
