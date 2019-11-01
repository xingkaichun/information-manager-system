package com.xingkaichun.information.controller;

import com.alibaba.fastjson.JSON;
import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceCode;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForDetailsPage;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForHomeShowListPage;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTOForShowListPage;
import com.xingkaichun.information.dto.BbsArticle.request.*;
import com.xingkaichun.information.dto.BbsArticle.response.*;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForBbsShowList;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForHomeShowList;
import com.xingkaichun.information.dto.BbsArticleComment.request.AddBbsArticleCommentRequest;
import com.xingkaichun.information.service.BbsArticleCommentService;
import com.xingkaichun.information.service.BbsArticleService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsSession;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Api(value="论坛帖子controller",tags={"论坛帖子接口"})
@Controller
@RequestMapping(value = "/Bbs")
public class BbsArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BbsArticleController.class);

    @Autowired
    private BbsArticleService bbsArticleService;
    @Autowired
    private BbsArticleCommentService bbsArticleCommentService;

    //region 帖子相关
    @ApiOperation(value="发帖", notes="发帖")
    @ResponseBody
    @PostMapping("/AddBbsArticle")
    public ServiceResult<AddBbsArticleResponse> addBbsArticle(HttpServletRequest request, HttpServletResponse response, @RequestBody AddBbsArticleRequest addBbsArticleRequest){

        try {
            addBbsArticleRequest.setUserId(CommonUtilsSession.getUser(request).getUserId());
            if(CommonUtils.isNUllOrEmpty(addBbsArticleRequest.getTitle())){
                return FreshServiceResult.createFailFreshServiceResult("帖子标题不能为空");
            }
            if(CommonUtils.isNUllOrEmpty(addBbsArticleRequest.getContent())){
                return FreshServiceResult.createFailFreshServiceResult("帖子内容不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(addBbsArticleRequest.getBbsArticleId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配帖子Id,请不要填写");
            }
            addBbsArticleRequest.setBbsArticleId(String.valueOf(UUID.randomUUID()));

            bbsArticleService.addBbsArticle(addBbsArticleRequest);
            return FreshServiceResult.createSuccessServiceResult("发帖成功",null);
        } catch (Exception e) {
            String message = "发帖出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
    }

    private String getRequestString(Object request) {
        if(request==null){
            return "";
        }
        return JSON.toJSONString(request);
    }

    @ApiOperation(value="随机获取帖子", notes="随机获取帖子")
    @ResponseBody
    @PostMapping("/QueryBbsArticleByRand")
    public ServiceResult<QueryBbsArticleByRandResponse> queryBbsArticleByRand(@RequestBody QueryBbsArticleByRandRequest request){

        try {
            List<BbsArticleDTO> bbsArticleDTOList = bbsArticleService.queryBbsArticleByRand();

            QueryBbsArticleByRandResponse queryBbsArticleByRandResponse = new QueryBbsArticleByRandResponse();
            queryBbsArticleByRandResponse.setBbsArticleDTOList(bbsArticleDTOList);

            return ServiceResult.createSuccessServiceResult("随机获取帖子成功",queryBbsArticleByRandResponse);
        } catch (Exception e) {
            String message = "随机获取帖子出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="帖子分页", notes="帖子分页")
    @ResponseBody
    @PostMapping("/QueryBbsArticle")
    public ServiceResult<QueryBbsArticleResponse> queryBbsArticle(@RequestBody QueryBbsArticleRequest request){

        try {
            PageInformation<BbsArticleDTOForShowListPage> bbsArticleDTOPageInformation = bbsArticleService.queryBbsArticle(request);

            QueryBbsArticleResponse queryBbsArticleResponse = new QueryBbsArticleResponse();
            queryBbsArticleResponse.setBbsArticleDTOPageInformation(bbsArticleDTOPageInformation);

            return ServiceResult.createSuccessServiceResult("帖子分页成功",queryBbsArticleResponse);
        } catch (Exception e) {
            String message = "帖子分页出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="获取用户帖子", notes="获取用户帖子")
    @ResponseBody
    @PostMapping("/QueryBbsArticleByUserId")
    public ServiceResult<QueryBbsArticleByUserIdResponse> queryBbsArticleByUserId(HttpServletRequest req, @RequestBody QueryBbsArticleByUserIdRequest request){

        try {
            String userId = CommonUtilsSession.getUser(req).getUserId();
            request.setUserId(userId);
            PageInformation<BbsArticleDTOForHomeShowListPage> bbsArticleDTOList = bbsArticleService.queryBbsArticleByUserId(request);

            QueryBbsArticleByUserIdResponse queryBbsArticleByUserIdResponse = new QueryBbsArticleByUserIdResponse();
            queryBbsArticleByUserIdResponse.setBbsArticleDTOList(bbsArticleDTOList);

            return ServiceResult.createSuccessServiceResult("获取用户帖子成功",queryBbsArticleByUserIdResponse);
        } catch (Exception e) {
            String message = "获取用户帖子出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="获取帖子详情", notes="获取帖子详情")
    @ResponseBody
    @PostMapping("/QueryBbsArticleDetailByBbsArticleId")
    public ServiceResult<QueryBbsArticleDetailByBbsArticleIdResponse> queryBbsArticleDetailByBbsArticleId(@RequestBody QueryBbsArticleDetailByBbsArticleIdRequest request){

        try {
            ServiceResult<BbsArticleDTOForDetailsPage> bbsArticleDTOServiceResult = bbsArticleService.queryBbsArticleDetailByBbsArticleId(request.getBbsArticleId());
            if(bbsArticleDTOServiceResult.getServiceCode()== ServiceCode.FAIL){
                return FreshServiceResult.createFailFreshServiceResult(bbsArticleDTOServiceResult.getMessage());
            }

            BbsArticleDTOForDetailsPage bbsArticleDTO = bbsArticleDTOServiceResult.getResult();
            QueryBbsArticleDetailByBbsArticleIdResponse queryBbsArticleDetailByBbsArticleIdResponse = new QueryBbsArticleDetailByBbsArticleIdResponse();
            queryBbsArticleDetailByBbsArticleIdResponse.setBbsArticleDTO(bbsArticleDTO);

            return ServiceResult.createSuccessServiceResult("获取帖子详情成功",queryBbsArticleDetailByBbsArticleIdResponse);
        } catch (Exception e) {
            String message = "获取帖子详情出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }
    //endregion

    //region 帖子评论相关
    @ApiOperation(value="评论帖子", notes="评论帖子")
    @ResponseBody
    @PostMapping("/AddBbsArticleComment")
    public FreshServiceResult addBbsArticleComment(HttpServletRequest request, HttpServletResponse response,@RequestBody AddBbsArticleCommentRequest addBbsArticleCommentRequest){
        try {
            addBbsArticleCommentRequest.setUserId(CommonUtilsSession.getUser(request).getUserId());
            return bbsArticleCommentService.addBbsArticleComment(addBbsArticleCommentRequest);
        } catch (Exception e) {
            String message = "评论帖子出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="获取用户帖子评论", notes="获取用户帖子评论")
    @ResponseBody
    @PostMapping("/QueryBbsArticleCommentByUser")
    public ServiceResult<QueryBbsArticleCommentByUserResponse> queryBbsArticleCommentByUser(HttpServletRequest req, @RequestBody QueryBbsArticleCommentByUserRequest request){

        try {
            String userId = CommonUtilsSession.getUser(req).getUserId();
            request.setUserId(userId);
            PageInformation<BbsArticleCommentDTOForHomeShowList> bbsArticleDTOList = bbsArticleService.queryBbsArticleCommentByUser(request);

            QueryBbsArticleCommentByUserResponse response = new QueryBbsArticleCommentByUserResponse();
            response.setBbsArticleCommentDTOPageInformation(bbsArticleDTOList);

            return ServiceResult.createSuccessServiceResult("获取用户帖子成功",response);
        } catch (Exception e) {
            String message = "获取用户帖子出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="获取帖子评论", notes="获取帖子评论")
    @ResponseBody
    @PostMapping("/QueryBbsArticleCommentByBbsArticleId")
    public ServiceResult<QueryBbsArticleCommentByBbsArticleIdResponse> queryBbsArticleCommentByBbsArticleId(@RequestBody QueryBbsArticleCommentByBbsArticleIdRequest request){
        try {
            PageInformation<BbsArticleCommentDTOForBbsShowList> bbsArticleDTOList = bbsArticleService.queryBbsArticleCommentByBbsArticleId(request);

            QueryBbsArticleCommentByBbsArticleIdResponse response = new QueryBbsArticleCommentByBbsArticleIdResponse();
            response.setBbsArticleCommentDTOInformation(bbsArticleDTOList);

            return ServiceResult.createSuccessServiceResult("获取帖子评论成功",response);
        } catch (Exception e) {
            String message = "获取帖子评论出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="获取帖子评论的评论", notes="获取帖子评论的评论")
    @ResponseBody
    @PostMapping("/QueryBbsArticleCommentByForBbsArticleCommentId")
    public ServiceResult<QueryBbsArticleCommentByForBbsArticleCommentIdResponse> queryBbsArticleCommentByForBbsArticleCommentId(@RequestBody QueryBbsArticleCommentByForBbsArticleCommentIdRequest request){
        try {
            PageInformation<BbsArticleCommentDTOForBbsShowList> bbsArticleDTOList = bbsArticleService.queryBbsArticleCommentByForBbsArticleCommentId(request);

            QueryBbsArticleCommentByForBbsArticleCommentIdResponse response = new QueryBbsArticleCommentByForBbsArticleCommentIdResponse();
            response.setBbsArticleCommentDTOInformation(bbsArticleDTOList);

            return ServiceResult.createSuccessServiceResult("获取帖子评论的评论成功",response);
        } catch (Exception e) {
            String message = "获取帖子评论的评论出错";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="获取两个人的对话评论", notes="获取两个人的对话评论")
    @ResponseBody
    @PostMapping("/queryTwoUserBbsArticleComment")
    public ServiceResult<QueryTwoUserBbsArticleCommentResponse> queryTwoUserBbsArticleComment(@RequestBody QueryTwoUserBbsArticleCommentRequest request){
        try {
            List<BbsArticleCommentDTOForBbsShowList> bbsArticleCommentDTOList = bbsArticleService.queryTwoUserBbsArticleComment(request);

            QueryTwoUserBbsArticleCommentResponse response = new QueryTwoUserBbsArticleCommentResponse();
            response.setBbsArticleCommentDTOList(bbsArticleCommentDTOList);

            return ServiceResult.createSuccessServiceResult("获取两个人的对话评论",response);
        } catch (Exception e) {
            String message = "获取两个人的对话评论";
            LOGGER.error(message+getRequestString(request),e);
            return FreshServiceResult.createFailServiceResult(message);
        } finally {
        }
    }
    //endregion
}
