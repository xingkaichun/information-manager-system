package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BbsArticleCommentDao;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTO;
import com.xingkaichun.information.dto.BbsArticleComment.request.AddBbsArticleCommentRequest;
import com.xingkaichun.information.model.BbsArticleCommentDomain;
import com.xingkaichun.information.service.BbsArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "bbsArticleCommentService")
public class BbsArticleCommentServiceImpl implements BbsArticleCommentService {

    @Autowired
    private BbsArticleCommentDao bbsArticleCommentDao;

    @Override
    public int AddBbsArticleComment(AddBbsArticleCommentRequest addBbsArticleCommentRequest) {
        return bbsArticleCommentDao.addBbsArticleComment(classCast(addBbsArticleCommentRequest));
    }

    private BbsArticleCommentDomain classCast(BbsArticleCommentDTO bbsArticleCommentDTO) {
        BbsArticleCommentDomain domain = new BbsArticleCommentDomain();
        domain.setBbsArticleCommentId(bbsArticleCommentDTO.getBbsArticleCommentId());
        domain.setBbsArticleId(bbsArticleCommentDTO.getBbsArticleId());
        domain.setContent(bbsArticleCommentDTO.getContent());
        domain.setCreateTime(bbsArticleCommentDTO.getCreateTime());
        domain.setSoftDelete(bbsArticleCommentDTO.isSoftDelete());
        domain.setUserId(bbsArticleCommentDTO.getUserId());
        domain.setParentBbsArticleCommentId(bbsArticleCommentDTO.getParentBbsArticleCommentId());
        return domain;
    }


}
