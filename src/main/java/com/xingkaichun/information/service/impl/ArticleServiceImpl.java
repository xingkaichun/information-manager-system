package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.ArticleDao;
import com.xingkaichun.information.dao.FileDao;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.DeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.dto.article.request.UpdateArticleRequest;
import com.xingkaichun.information.dto.base.PageCondition;
import com.xingkaichun.information.dto.base.PageInformation;
import com.xingkaichun.information.dto.file.FileDto;
import com.xingkaichun.information.model.ArticleDomain;
import com.xingkaichun.information.model.FileDomain;
import com.xingkaichun.information.service.ArticleService;
import com.xingkaichun.information.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private FileDao fileDao;

    @Override
    public int addArticle(AddArticleRequest addArticleRequest) {
        return articleDao.addArticle(classCast(addArticleRequest));
    }

    @Override
    public int deleteArticle(DeleteArticleRequest deleteArticleRequest) {
        return articleDao.deleteArticle(deleteArticleRequest);
    }

    @Override
    public PageInformation<ArticleDTO> queryArticle(QueryArticleRequest queryArticleRequest) {
        PageCondition pageCondition = queryArticleRequest.getPageCondition();
        //PageHelper.startPage(queryArticleRequest.getPageCondition().getPageNum(), queryArticleRequest.getPageCondition().getPageSize());
        List<ArticleDomain> articleDomainList = articleDao.queryArticle(queryArticleRequest);
        int totalCount = articleDao.queryArticleTotalCount(queryArticleRequest);
        //PageInfo result = new PageInfo(articleDomainList);
        PageInformation<ArticleDTO> articleDTOPageInformation = new PageInformation<>(pageCondition.getPageNum(),pageCondition.getPageSize(),totalCount,classCast(articleDomainList));
        return articleDTOPageInformation;
    }

    @Override
    public int updateArticle(UpdateArticleRequest updateArticleRequest) {
        return articleDao.updateArticle(classCast(updateArticleRequest));
    }

    @Override
    public boolean hasArticleInCategoryId(String categoryId) {
        QueryArticleRequest queryArticleRequest = new QueryArticleRequest();
        queryArticleRequest.setCategoryId(categoryId);
        int totalCount = articleDao.queryArticleTotalCount(queryArticleRequest);
        return totalCount>0?true:false;
    }

    private List<ArticleDTO> classCast(List<ArticleDomain> articleDomainList) {
        if(articleDomainList == null){
            return null;
        }
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        articleDomainList.forEach(articleDomain -> articleDTOList.add(classCast(articleDomain)));
        return articleDTOList;
    }

    private ArticleDTO classCast(ArticleDomain articleDomain) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleId(articleDomain.getArticleId());
        articleDTO.setCategoryId(articleDomain.getCategoryId());
        articleDTO.setUserId(articleDomain.getUserId());
        articleDTO.setTitle(articleDomain.getTitle());
        articleDTO.setContent(articleDomain.getContent());
        articleDTO.setCreateTime(articleDomain.getCreateTime());
        articleDTO.setLastEditTime(articleDomain.getLastEditTime());
        articleDTO.setAttachedFiles(articleDomain.getAttachedFiles());
        String attachedFiles = articleDomain.getAttachedFiles();
        if(CommonUtils.isNUllOrEmpty(attachedFiles)){
            articleDTO.setAttachedFilesDetails(null);
        }else{
            String[] attachedFileIds = attachedFiles.split(",");
            List<FileDto> attachedFileDetails = new ArrayList<>();
            for(String af:attachedFileIds){
                List<FileDomain> fileDomains = fileDao.queryFile(new FileDomain(af,null,null,null,null));
                if(!CommonUtils.isNUllOrEmpty(fileDomains)){
                    attachedFileDetails.addAll(FileServiceImpl.classCast(fileDomains));
                }
            }
            articleDTO.setAttachedFilesDetails(attachedFileDetails);
        }
        articleDTO.setIsSoftDelete(articleDomain.isSoftDelete());
        return articleDTO;
    }

    private ArticleDomain classCast(ArticleDTO articleDTO) {
        ArticleDomain articleDomain = new ArticleDomain();
        articleDomain.setArticleId(articleDTO.getArticleId());
        articleDomain.setCategoryId(articleDTO.getCategoryId());
        articleDomain.setUserId(articleDTO.getUserId());
        articleDomain.setTitle(articleDTO.getTitle());
        articleDomain.setContent(articleDTO.getContent());
        articleDomain.setCreateTime(articleDTO.getCreateTime());
        articleDomain.setLastEditTime(articleDTO.getLastEditTime());
        articleDomain.setAttachedFiles(articleDTO.getAttachedFiles());
        Boolean isSoftDelete = articleDTO.getIsSoftDelete();
        articleDomain.setSoftDelete(CommonUtils.isNUll(isSoftDelete)?false:isSoftDelete);
        return articleDomain;
    }
}
