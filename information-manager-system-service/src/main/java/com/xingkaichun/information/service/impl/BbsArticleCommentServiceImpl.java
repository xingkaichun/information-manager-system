package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BbsArticleCommentDao;
import com.xingkaichun.information.dao.BbsArticleDao;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTO;
import com.xingkaichun.information.dto.BbsArticleComment.request.AddBbsArticleCommentRequest;
import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.information.model.BbsArticleCommentDomain;
import com.xingkaichun.information.model.BbsArticleDomain;
import com.xingkaichun.information.service.BbsArticleCommentService;
import com.xingkaichun.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "bbsArticleCommentService")
public class BbsArticleCommentServiceImpl implements BbsArticleCommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BbsArticleCommentServiceImpl.class);

    @Autowired
    private BbsArticleDao bbsArticleDao;
    @Autowired
    private BbsArticleCommentDao bbsArticleCommentDao;

    @Override
    public FreshServiceResult AddBbsArticleComment(AddBbsArticleCommentRequest addBbsArticleCommentRequest) {
        try {
            if(CommonUtils.isNUllOrEmpty(addBbsArticleCommentRequest.getBbsArticleId())){
                return FreshServiceResult.createFailFreshServiceResult("被评论的帖子ID不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(addBbsArticleCommentRequest.getBbsArticleCommentId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配帖子评论记录的Id,请不要填写");
            } else {
                addBbsArticleCommentRequest.setBbsArticleCommentId(String.valueOf(UUID.randomUUID()));
            }

            //若被评论的是帖子，校验被评论的帖子的存在
            BbsArticleDomain bbsArticleDomain = bbsArticleDao.queryBbsArticleByBbsArticleId(addBbsArticleCommentRequest.getBbsArticleId());
            if(bbsArticleDomain==null){
                return FreshServiceResult.createFailFreshServiceResult("被评论的帖子不存在");
            }
            //若被评论的是帖子评论，校验被评论的帖子评论的存在
            String parentBbsArticleCommentId = addBbsArticleCommentRequest.getParentBbsArticleCommentId();
            if(!CommonUtils.isNUllOrEmpty(parentBbsArticleCommentId)){
                BbsArticleCommentDomain bbsArticleCommentDomain = bbsArticleCommentDao.querybbsArticleCommentBybbsArticleCommentId(parentBbsArticleCommentId);
                if(bbsArticleCommentDomain==null){
                    return FreshServiceResult.createFailFreshServiceResult("被评论的帖子评论不存在");
                }
                addBbsArticleCommentRequest.setForBbsArticleCommentId(bbsArticleCommentDomain.getBbsArticleCommentId());
            }else{
                addBbsArticleCommentRequest.setForBbsArticleCommentId(addBbsArticleCommentRequest.getBbsArticleCommentId());
            }
            bbsArticleCommentDao.addBbsArticleComment(classCast(addBbsArticleCommentRequest));

            //重新计算评论数目
            int numberOfComment = bbsArticleCommentDao.queryNumberOfComment(addBbsArticleCommentRequest.getBbsArticleId());
            BbsArticleDomain updateNumberOfComment = new BbsArticleDomain();
            updateNumberOfComment.setBbsArticleId(addBbsArticleCommentRequest.getBbsArticleId());
            updateNumberOfComment.setNumberOfComment(numberOfComment);
            bbsArticleDao.updateBbsArticle(updateNumberOfComment);
        } catch (Exception e) {
            String message = "评论帖子出错";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
        return FreshServiceResult.createSuccessFreshServiceResult("评论帖子成功");
    }


    public static List<BbsArticleCommentDTO> classCast(List<BbsArticleCommentDomain> domainList) {
        if(CommonUtils.isNUllOrEmpty(domainList)){
            return null;
        }
        List<BbsArticleCommentDTO> dtoList = new ArrayList<>();
        for (BbsArticleCommentDomain domain:domainList){
            dtoList.add(classCast(domain));
        }
        return dtoList;
    }

    private static BbsArticleCommentDTO classCast(BbsArticleCommentDomain domain) {
        BbsArticleCommentDTO dto = new BbsArticleCommentDTO();
        dto.setBbsArticleCommentId(domain.getBbsArticleCommentId());
        dto.setBbsArticleId(domain.getBbsArticleId());
        dto.setContent(domain.getContent());
        dto.setCreateTime(domain.getCreateTime());
        dto.setSoftDelete(domain.isSoftDelete());
        dto.setUserId(domain.getUserId());
        dto.setParentBbsArticleCommentId(domain.getParentBbsArticleCommentId());
        return dto;
    }

    private static BbsArticleCommentDomain classCast(BbsArticleCommentDTO bbsArticleCommentDTO) {
        BbsArticleCommentDomain domain = new BbsArticleCommentDomain();
        domain.setBbsArticleCommentId(bbsArticleCommentDTO.getBbsArticleCommentId());
        domain.setBbsArticleId(bbsArticleCommentDTO.getBbsArticleId());
        domain.setContent(bbsArticleCommentDTO.getContent());
        domain.setCreateTime(bbsArticleCommentDTO.getCreateTime());
        domain.setSoftDelete(bbsArticleCommentDTO.isSoftDelete());
        domain.setUserId(bbsArticleCommentDTO.getUserId());
        domain.setParentBbsArticleCommentId(bbsArticleCommentDTO.getParentBbsArticleCommentId());
        domain.setForBbsArticleCommentId(bbsArticleCommentDTO.getForBbsArticleCommentId());
        return domain;
    }


}
