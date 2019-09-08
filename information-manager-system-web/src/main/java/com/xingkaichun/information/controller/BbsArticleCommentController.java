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

        return bbsArticleCommentService.AddBbsArticleComment(addBbsArticleCommentRequest);
    }
}
