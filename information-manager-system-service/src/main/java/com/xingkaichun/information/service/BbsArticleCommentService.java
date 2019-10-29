package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.BbsArticleComment.request.AddBbsArticleCommentRequest;
import com.xingkaichun.common.dto.base.FreshServiceResult;

public interface BbsArticleCommentService {

    FreshServiceResult addBbsArticleComment(AddBbsArticleCommentRequest addBbsArticleCommentRequest);

}
