package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.ArticleDao;
import com.xingkaichun.information.dao.FileDao;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.PhysicsDeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.dto.article.request.UpdateArticleRequest;
import com.xingkaichun.information.dto.base.PageCondition;
import com.xingkaichun.information.dto.base.PageInformation;
import com.xingkaichun.information.dto.category.CategoryDTO;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.dto.category.response.QueryCategoryResponse;
import com.xingkaichun.information.dto.file.FileDto;
import com.xingkaichun.information.model.ArticleDomain;
import com.xingkaichun.information.model.FileDomain;
import com.xingkaichun.information.service.ArticleService;
import com.xingkaichun.information.service.CategoryService;
import com.xingkaichun.information.utils.CommonUtils;
import com.xingkaichun.information.utils.CommonUtilsFile;
import com.xingkaichun.information.utils.CommonUtilsList;
import com.xingkaichun.information.utils.CommonUtilsString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private FileDao fileDao;

    @Value("${project.template.articleTemplateFilePath}")
    public String articleTemplateFilePath;
    @Value("${project.template.articleTemplateProduceFileSaveDirectory}")
    public String articleTemplateProduceFileSaveDirectory;


    @Override
    public int addArticle(AddArticleRequest addArticleRequest) {
        return articleDao.addArticle(classCast(addArticleRequest));
    }

    @Override
    public int physicsDeleteArticle(PhysicsDeleteArticleRequest physicsDeleteArticleRequest) {
        return articleDao.physicsDeleteArticle(physicsDeleteArticleRequest);
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

    @Override
    public boolean isSoftDelete(String articleId) {
        ArticleDomain articleDomain = articleDao.queryArticleByArticleId(articleId);
        return articleDomain.isSoftDelete();
    }

    @Override
    public void createArticleHtml() throws IOException {
        QueryArticleRequest queryArticleRequest = new QueryArticleRequest();
        List<ArticleDomain> articleDomainList = articleDao.queryArticle(queryArticleRequest);
        String content = CommonUtilsFile.readFileContent(articleTemplateFilePath);
        QueryCategoryResponse queryCategoryResponse = categoryService.queryCategoryReturnHierarchicalStructure(new QueryCategoryRequest());
        //书籍类别
        CategoryDTO bookCategoryDTO = queryCategoryResponse.getCategoryDTOList().stream().filter(categoryDTO -> categoryDTO.getCategoryId().equals("a6cfe490-f0f5-45c6-95fb-4c7c0ca38f79")).findFirst().get();
        for(ArticleDomain articleDomain:articleDomainList){
            String articleId = articleDomain.getArticleId();
            String articleTitle = CommonUtilsString.isNullOrEmpty(articleDomain.getTitle())?"":articleDomain.getTitle();
            //简介
            String articleInfo = "[无]" ;
            //如果是书籍下载页
            if(isSonCategory(bookCategoryDTO,articleDomain.getCategoryId())){
                articleInfo = articleTitle + "目录、" + articleTitle+"下载";
            }
            List<FileDto> fileDtoList = classCast2FileDtoList(articleDomain);
            String downFileHtml = "" ;
            if(!CommonUtilsList.isNullOrEmpty(fileDtoList)){
                for(FileDto fileDto:fileDtoList){
                    downFileHtml = downFileHtml + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style='color:red' href="+fileDto.getDownPath()+">"+fileDto.getFileName()+"下载<a>";
                }
            }
            String articleContent = CommonUtilsString.isNullOrEmpty(articleDomain.getContent())?"":articleDomain.getContent();
            String articleHtml = content.replace("[###con_tilte###]",articleTitle)
                    .replace("[###con_info###]",articleInfo+downFileHtml)
                    .replace("[###con_text###]",articleContent);
            CommonUtilsFile.writeFileContent(articleTemplateProduceFileSaveDirectory+articleId+".html",articleHtml);
        }
    }

    /**
     * 判断类别是否是类别DTO(或是其子类别)
     * @return
     */
    private boolean isSonCategory(CategoryDTO categoryDTO, String categoryId) {
        if(categoryDTO==null){
            return false;
        }
        if(categoryDTO.getCategoryId().equals(categoryId)){
            return true;
        }
        List<CategoryDTO> childrenCategoryDTOList = categoryDTO.getChildrenCategoryDTOList();
        if(childrenCategoryDTOList==null){
            return false;
        }else{
            for(CategoryDTO childrenCategoryDTO:childrenCategoryDTOList){
                if(isSonCategory(childrenCategoryDTO,categoryId)){
                    return true;
                }
            }
            return false;
        }
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
        List<FileDto> attachedFileDetails = classCast2FileDtoList(articleDomain);
        articleDTO.setAttachedFilesDetails(attachedFileDetails);
        articleDTO.setIsSoftDelete(articleDomain.isSoftDelete());

        articleDTO.setBookAuthor(articleDomain.getBookAuthor());
        articleDTO.setBookLanguage(articleDomain.getBookLanguage());
        articleDTO.setBookVersion(articleDomain.getBookVersion());
        articleDTO.setBookTranslateAuthor(articleDomain.getBookTranslateAuthor());
        articleDTO.setBookPublishingHouse(articleDomain.getBookPublishingHouse());
        articleDTO.setBookISBN(articleDomain.getBookISBN());
        return articleDTO;
    }

    /**
     * 将ArticleDomain的attachedFiles转换为文件FileDto对象
     */
    private List<FileDto> classCast2FileDtoList(ArticleDomain articleDomain) {
        String attachedFiles = articleDomain.getAttachedFiles();
        if(CommonUtils.isNUllOrEmpty(attachedFiles)){
            return null;
        }
        String[] attachedFileIds = attachedFiles.split(",");
        List<FileDto> attachedFileDetails = new ArrayList<>();
        for(String af:attachedFileIds){
            List<FileDomain> fileDomains = fileDao.queryFile(new FileDomain(af,null,null,null,null));
            if(!CommonUtils.isNUllOrEmpty(fileDomains)){
                attachedFileDetails.addAll(FileServiceImpl.classCast(fileDomains));
            }
        }
        return attachedFileDetails;
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

        articleDomain.setBookAuthor(articleDTO.getBookAuthor());
        articleDomain.setBookLanguage(articleDTO.getBookLanguage());
        articleDomain.setBookVersion(articleDTO.getBookVersion());
        articleDomain.setBookTranslateAuthor(articleDTO.getBookTranslateAuthor());
        articleDomain.setBookPublishingHouse(articleDTO.getBookPublishingHouse());
        articleDomain.setBookISBN(articleDTO.getBookISBN());
        return articleDomain;
    }
}
