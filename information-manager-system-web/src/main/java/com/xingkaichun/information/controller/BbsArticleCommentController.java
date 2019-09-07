package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.BbsArticleComment.request.AddBbsArticleCommentRequest;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.service.BbsArticleCommentService;
import com.xingkaichun.utils.CommonUtils;
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
import java.util.UUID;

@Api(value="论坛帖子评论controller",tags={"论坛帖子评论接口"})
@Controller
@RequestMapping(value = "/BbsArticleComment")
public class BbsArticleCommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BbsArticleCommentController.class);

    @Autowired
    private BbsArticleCommentService bbsArticleCommentService;

    @ApiOperation(value="评论帖子", notes="评论帖子")
    @ResponseBody
    @PostMapping("/AddBbsArticleComment")
    public FreshServiceResult AddBbsArticleComment(HttpServletRequest request, HttpServletResponse response,@RequestBody AddBbsArticleCommentRequest addBbsArticleCommentRequest){

        try {
            if(CommonUtils.isNUllOrEmpty(addBbsArticleCommentRequest.getBbsArticleId())){
                return FreshServiceResult.createFailFreshServiceResult("被评论的帖子ID不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(addBbsArticleCommentRequest.getBbsArticleCommentId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配帖子评论记录的Id,请不要填写");
            }
            addBbsArticleCommentRequest.setBbsArticleId(String.valueOf(UUID.randomUUID()));

            //若被评论的是帖子，校验被评论的帖子的存在
            
            //若被评论的是帖子评论，校验被评论的帖子评论的存在



            bbsArticleCommentService.AddBbsArticleComment(addBbsArticleCommentRequest);
        } catch (Exception e) {
            String message = "评论帖子出错";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
        return FreshServiceResult.createSuccessFreshServiceResult("评论帖子成功");
    }
}
