package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import com.xingkaichun.information.dto.BbsArticle.request.AddBbsArticleRequest;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.service.BbsArticleService;
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

@Api(value="论坛帖子controller",tags={"论坛帖子接口"})
@Controller
@RequestMapping(value = "/BbsArticle")
public class BbsArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BbsArticleController.class);

    @Autowired
    private BbsArticleService bbsArticleService;

    @ApiOperation(value="发帖", notes="发帖")
    @ResponseBody
    @PostMapping("/AddBbsArticle")
    public FreshServiceResult addBbsArticle(HttpServletRequest request, HttpServletResponse response,@RequestBody AddBbsArticleRequest addBbsArticleRequest){

        try {
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
        } catch (Exception e) {
            String message = "发帖出错";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
        return FreshServiceResult.createSuccessFreshServiceResult("发帖成功");
    }

}
