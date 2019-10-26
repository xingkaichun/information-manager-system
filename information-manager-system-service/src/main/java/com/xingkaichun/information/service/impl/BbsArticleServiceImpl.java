package com.xingkaichun.information.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.common.dto.base.page.PageCondition;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dao.BbsArticleCommentDao;
import com.xingkaichun.information.dao.BbsArticleDao;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.request.AddBbsArticleRequest;
import com.xingkaichun.information.dto.BbsArticle.request.QueryBbsArticleRequest;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTO;
import com.xingkaichun.information.dto.user.UserInfo;
import com.xingkaichun.information.model.BbsArticleCommentDomain;
import com.xingkaichun.information.model.BbsArticleDomain;
import com.xingkaichun.information.service.BbsArticleService;
import com.xingkaichun.information.service.UserService;
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

    @Autowired
    private UserService userService;

    @Override
    public int addBbsArticle(AddBbsArticleRequest addBbsArticleRequest) {
        return bbsArticleDao.addBbsArticle(classCast(addBbsArticleRequest));
    }

    @Override
    public List<BbsArticleDTO> queryBbsArticleByRand() {
        List<BbsArticleDomain> bbsArticleDomainList = bbsArticleDao.queryBbsArticleByRand();
        List<BbsArticleDTO> bbsArticleDTOList = classCast(bbsArticleDomainList);
        fillBbsArticleDTO(bbsArticleDTOList);
        return bbsArticleDTOList;
    }

    private void fillBbsArticleDTO(List<BbsArticleDTO> bbsArticleDTOList) {
        if(bbsArticleDTOList == null){
            return;
        }
        for(BbsArticleDTO bbsArticleDTO:bbsArticleDTOList){
            fillBbsArticleDTO(bbsArticleDTO);
        }
    }
    private void fillBbsArticleDTO(BbsArticleDTO bbsArticleDTO) {
        UserInfo userInfo = userService.queryOneUserByUserId2(bbsArticleDTO.getUserId());
        bbsArticleDTO.setUserInfo(userInfo);
    }
    private void fillBbsArticleCommentDTO(List<BbsArticleCommentDTO> bbsArticleDTOList) {
        if(bbsArticleDTOList == null){
            return;
        }
        for(BbsArticleCommentDTO bbsArticleCommentDTO:bbsArticleDTOList){
            BbsArticleCommentDTO(bbsArticleCommentDTO);
        }
    }
    private void BbsArticleCommentDTO(BbsArticleCommentDTO bbsArticleCommentDTO) {
        UserInfo userInfo = userService.queryOneUserByUserId2(bbsArticleCommentDTO.getUserId());
        bbsArticleCommentDTO.setUserInfo(userInfo);
    }

    @Override
    public List<BbsArticleDTO> queryBbsArticleByUserId(String userId) {
        List<BbsArticleDomain>  bbsArticleDomainList = bbsArticleDao.queryBbsArticleByUserId(userId);
        List<BbsArticleDTO> bbsArticleDTOList = classCast(bbsArticleDomainList);
        fillBbsArticleDTO(bbsArticleDTOList);
        return bbsArticleDTOList;
    }

    @Override
    public ServiceResult<BbsArticleDTO> queryBbsArticleDetailByBbsArticleId(String bbsArticleId) {
        BbsArticleDomain bbsArticleDomain = bbsArticleDao.queryBbsArticleByBbsArticleId(bbsArticleId);
        if(bbsArticleDomain == null){
            return ServiceResult.createFailServiceResult("帖子不存在");
        }
        BbsArticleDTO bbsArticleDTO = classCast(bbsArticleDomain);
        fillBbsArticleDTO(bbsArticleDTO);

        List<BbsArticleCommentDomain> bbsArticleCommentDomainList = bbsArticleCommentDao.querybbsArticleCommentBybbsArticleId(bbsArticleId);
        List<BbsArticleCommentDTO> bbsArticleDTOList = BbsArticleCommentServiceImpl.classCast(bbsArticleCommentDomainList);
        fillBbsArticleCommentDTO(bbsArticleDTOList);

        List<BbsArticleCommentDTO> parentBbsArticleCommentDTOList = CommonUtilBbsArticleCommentDTO.parentBbsArticleCommentDTOList(bbsArticleDTOList);

        bbsArticleDTO.setBbsArticleCommentDTOList(parentBbsArticleCommentDTOList);
        return ServiceResult.createSuccessServiceResult("获取帖子详情成功",bbsArticleDTO);
    }

    @Override
    public PageInformation<BbsArticleDTO> queryBbsArticle(QueryBbsArticleRequest request) {
        PageCondition pageCondition = request.getPageCondition();
        PageHelper.startPage(request.getPageCondition().getPageNum(),request.getPageCondition().getPageSize());
        Page<BbsArticleDomain> page = bbsArticleDao.queryBbsArticle(request);
        PageInformation<BbsArticleDTO> articleDTOPageInformation = new PageInformation<BbsArticleDTO>(pageCondition.getPageNum(),pageCondition.getPageSize(),page.getTotal(),classCast(page.getResult()));
        return articleDTOPageInformation;
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
        bbsArticleDTO.setLastEditTime(bbsArticleDomain.getLastEditTime());
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
        bbsArticleDomain.setLastEditTime(bbsArticleDTO.getLastEditTime());
        return bbsArticleDomain;
    }
}
