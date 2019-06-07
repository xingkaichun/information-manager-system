package com.xingkaichun.information.service.user.impl;

import com.xingkaichun.information.dao.ArticleDao;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.DeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.dto.article.request.UpdateArticleRequest;
import com.xingkaichun.information.model.ArticleDomain;
import com.xingkaichun.information.service.user.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public int addArticle(AddArticleRequest addArticleRequest) {
        return articleDao.addArticle(classCast(addArticleRequest));
    }

    @Override
    public int deleteArticle(DeleteArticleRequest deleteArticleRequest) {
        return articleDao.deleteArticle(deleteArticleRequest);
    }

    @Override
    public List<ArticleDTO> queryArticle(QueryArticleRequest queryArticleRequest) {
        List<ArticleDomain> articleDomainList = articleDao.queryArticle(queryArticleRequest);
        return classCast(articleDomainList);
    }

    @Override
    public int updateArticle(UpdateArticleRequest updateArticleRequest) {
        return articleDao.updateArticle(classCast(updateArticleRequest));
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
        return articleDomain;
    }
}
