package com.xingkaichun.information.controller;

import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsHtml;
import com.xingkaichun.utils.CommonUtilsSession;
import com.xingkaichun.utils.CommonUtilsString;
import com.xingkaichun.information.dto.article.ArticleDTO;
import com.xingkaichun.information.dto.article.request.AddArticleRequest;
import com.xingkaichun.information.dto.article.request.PhysicsDeleteArticleRequest;
import com.xingkaichun.information.dto.article.request.QueryArticleRequest;
import com.xingkaichun.information.dto.article.request.UpdateArticleRequest;
import com.xingkaichun.information.dto.article.response.QueryArticleResponse;
import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.PageInformation;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.category.CategoryDTO;
import com.xingkaichun.information.service.ArticleService;
import com.xingkaichun.information.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.UUID;

@Api(value="文章controller",tags={"文章操作接口"})
@Controller
@RequestMapping(value = "/Article")
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value="新增文章", notes="新增文章")
    @ResponseBody
    @PostMapping("/AddArticle")
    public FreshServiceResult addArticle(@RequestBody AddArticleRequest addArticleRequest, HttpServletRequest request){
        try{
            addArticleRequest.setUserId(CommonUtilsSession.getUser(request).getUserId());
            if(CommonUtils.isNUllOrEmpty(addArticleRequest.getTitle())){
                return FreshServiceResult.createFailFreshServiceResult("文章标题不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(addArticleRequest.getArticleId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配文章ID，不允许赋值");
            }else{
                addArticleRequest.setArticleId(String.valueOf(UUID.randomUUID()));
            }
            if(CommonUtils.isNUllOrEmpty(addArticleRequest.getCategoryId())){
                return FreshServiceResult.createFailFreshServiceResult("文章类别不能为空");
            }else{
                CategoryDTO categoryDTO = categoryService.queryCategoryByCategoryId(addArticleRequest.getCategoryId());
                if(CommonUtils.isNUllOrEmpty(categoryDTO)){
                    return FreshServiceResult.createFailFreshServiceResult("文章类别不存在");
                }
            }
            //默认值
            addArticleRequest.setIsSoftDelete(false);
            addArticleRequest.setLastEditTime(new Date(System.currentTimeMillis()));
            handlerArticleContent(addArticleRequest);
            articleService.addArticle(addArticleRequest);
            //创建文章静态Html页面
            QueryArticleRequest queryArticleRequest = new QueryArticleRequest();
            queryArticleRequest.setArticleId(addArticleRequest.getArticleId());
            articleService.createArticleHtml(queryArticleRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("新增文章成功");
        } catch (Exception e){
            String message = "新增文章失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="删除文章", notes="删除文章")
    @ResponseBody
    @PostMapping("/PhysicsDeleteArticle")
    public FreshServiceResult physicsDeleteArticle(@RequestBody PhysicsDeleteArticleRequest physicsDeleteArticleRequest){
        try{
            String articleId = physicsDeleteArticleRequest.getArticleId();
            if(CommonUtils.isNUllOrEmpty(articleId)){
                return FreshServiceResult.createFailFreshServiceResult("ArticleId不能为空");
            }
            if(!articleService.isSoftDelete(articleId)){
                return FreshServiceResult.createFailFreshServiceResult("物理删除文章前需置文章软删除标志为true");
            }
            articleService.physicsDeleteArticle(physicsDeleteArticleRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("删除文章成功");
        } catch (Exception e){
            String message = "删除文章失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="查询文章", notes="查询文章")
    @ResponseBody
    @PostMapping("/QueryArticle")
    public ServiceResult<QueryArticleResponse> queryArticle(@RequestBody QueryArticleRequest queryArticleRequest){
        try{
            QueryArticleResponse queryArticleResponse = new QueryArticleResponse();
            PageInformation<ArticleDTO> articleDTOPageInformation = articleService.queryArticle(queryArticleRequest);
            queryArticleResponse.setArticleDTOPageInformation(articleDTOPageInformation);
            return ServiceResult.createSuccessServiceResult("查询文章成功",queryArticleResponse);
        } catch (Exception e){
            String message = "查询文章失败";
            LOGGER.error(message,e);
            return ServiceResult.createFailServiceResult(message);
        }
    }

    @ApiOperation(value="更新文章", notes="更新文章")
    @ResponseBody
    @PostMapping("/UpdateArticle")
    public FreshServiceResult updateArticle(@RequestBody UpdateArticleRequest updateArticleRequest){
        try{
            String articleId = updateArticleRequest.getArticleId();
            if(CommonUtils.isNUllOrEmpty(articleId)){
                return FreshServiceResult.createFailFreshServiceResult("ArticleId不能为空");
            }

            if(!CommonUtils.isNUllOrEmpty(updateArticleRequest.getCategoryId())){
                CategoryDTO categoryDTO = categoryService.queryCategoryByCategoryId(updateArticleRequest.getCategoryId());
                if(CommonUtils.isNUllOrEmpty(categoryDTO)){
                    return FreshServiceResult.createFailFreshServiceResult("文章类别不存在");
                }
            }
            handlerArticleContent(updateArticleRequest);
            articleService.updateArticle(updateArticleRequest);
            //创建文章静态Html页面
            QueryArticleRequest queryArticleRequest = new QueryArticleRequest();
            queryArticleRequest.setArticleId(updateArticleRequest.getArticleId());
            articleService.createArticleHtml(queryArticleRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("更新文章成功");
        } catch (Exception e){
            String message = "更新文章失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="生成文章静态Html页面", notes="生成文章静态Html页面")
    @ResponseBody
    @PostMapping("/createArticleHtml")
    public FreshServiceResult createArticleHtml(QueryArticleRequest queryArticleRequest){
        try{
            articleService.createArticleHtml(queryArticleRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("生成文章静态Html页面成功");
        } catch (Exception e){
            String message = "生成文章静态Html页面失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    /**
     * 对article内容做进一步处理
     */
    private void handlerArticleContent(ArticleDTO articleDTO){
        String content = articleDTO.getContent();
        if(!CommonUtilsString.isNullOrEmpty(content)){
            articleDTO.setContent(CommonUtilsHtml.handlerArticleContent(content));
            articleDTO.setContent(content);
        }
    }
}
