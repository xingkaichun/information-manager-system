package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BbsArticleCommentDao;
import com.xingkaichun.information.dao.BbsArticleDao;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.request.AddBbsArticleRequest;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTO;
import com.xingkaichun.information.model.BbsArticleCommentDomain;
import com.xingkaichun.information.model.BbsArticleDomain;
import com.xingkaichun.information.service.BbsArticleCommentService;
import com.xingkaichun.information.service.BbsArticleService;
import com.xingkaichun.utils.CommonUtilBbsArticleCommentDTO;
import com.xingkaichun.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "bbsArticleService")
public class BbsArticleServiceImpl implements BbsArticleService {

    @Autowired
    private BbsArticleDao bbsArticleDao;
    @Autowired
    private BbsArticleCommentDao bbsArticleCommentDao;

    @Override
    public int addBbsArticle(AddBbsArticleRequest addBbsArticleRequest) {
        return bbsArticleDao.addBbsArticle(classCast(addBbsArticleRequest));
    }

    @Override
    public List<BbsArticleDTO> queryBbsArticleByRand() {
        List<BbsArticleDomain>  bbsArticleDomainList = bbsArticleDao.queryBbsArticleByRand();
        return classCast(bbsArticleDomainList);
    }

    @Override
    public List<BbsArticleDTO> queryBbsArticleByUserId(String userId) {
        List<BbsArticleDomain>  bbsArticleDomainList = bbsArticleDao.queryBbsArticleByUserId(userId);
        return classCast(bbsArticleDomainList);
    }

    @Override
    public BbsArticleDTO queryBbsArticleDetailByBbsArticleId(String bbsArticleId) {
        BbsArticleDomain bbsArticleDomain = bbsArticleDao.queryBbsArticleByBbsArticleId(bbsArticleId);
        BbsArticleDTO bbsArticleDTO = classCast(bbsArticleDomain);

        List<BbsArticleCommentDomain> bbsArticleCommentDomainList = bbsArticleCommentDao.querybbsArticleCommentBybbsArticleId(bbsArticleId);
        List<BbsArticleCommentDTO> parentBbsArticleCommentDTOList = CommonUtilBbsArticleCommentDTO.parentBbsArticleCommentDTOList(BbsArticleCommentServiceImpl.classCast(bbsArticleCommentDomainList));

        bbsArticleDTO.setBbsArticleCommentDTOList(parentBbsArticleCommentDTOList);
        return bbsArticleDTO;
    }

    private List<BbsArticleDTO> classCast(List<BbsArticleDomain> bbsArticleDomainList) {
        if(CommonUtils.isNUllOrEmpty(bbsArticleDomainList)){
            return null;
        }
        List<BbsArticleDTO> dtoList = new ArrayList<>();
        for (BbsArticleDomain bbsArticleDomain:bbsArticleDomainList){
            dtoList.add(classCast(bbsArticleDomain));
        }
        return dtoList;
    }

    private BbsArticleDTO classCast(BbsArticleDomain bbsArticleDomain) {
        BbsArticleDTO bbsArticleDTO = new BbsArticleDTO();
        bbsArticleDTO.setBbsArticleId(bbsArticleDomain.getBbsArticleId());
        bbsArticleDTO.setContent(bbsArticleDomain.getContent());
        bbsArticleDTO.setCreateTime(bbsArticleDomain.getCreateTime());
        bbsArticleDTO.setSoftDelete(bbsArticleDomain.isSoftDelete());
        bbsArticleDTO.setTitle(bbsArticleDomain.getTitle());
        bbsArticleDTO.setUserId(bbsArticleDomain.getUserId());
        return bbsArticleDTO;
    }

    private BbsArticleDomain classCast(BbsArticleDTO bbsArticleDTO) {
        BbsArticleDomain bbsArticleDomain = new BbsArticleDomain();
        bbsArticleDomain.setBbsArticleId(bbsArticleDTO.getBbsArticleId());
        bbsArticleDomain.setContent(bbsArticleDTO.getContent());
        bbsArticleDomain.setCreateTime(bbsArticleDTO.getCreateTime());
        bbsArticleDomain.setSoftDelete(bbsArticleDTO.isSoftDelete());
        bbsArticleDomain.setTitle(bbsArticleDTO.getTitle());
        bbsArticleDomain.setUserId(bbsArticleDTO.getUserId());
        return bbsArticleDomain;
    }
}
