package com.xingkaichun.information.controller;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.favorite.FavoriteType;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;
import com.xingkaichun.information.dto.favorite.request.AddFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.favorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.favorite.response.QueryUserFavoriteBookListResponse;
import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.BookService;
import com.xingkaichun.information.service.UserFavoriteService;
import com.xingkaichun.utils.CommonUtilsSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/UserFavorite")
public class UserFavoriteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFavoriteService userFavoriteService;
    @Autowired
    private BookService bookService;

    @ResponseBody
    @PostMapping("/AddFavorite")
    public FreshServiceResult addFavorite(@RequestBody AddFavoriteRequest request, HttpServletRequest httpServletRequest){
        try{
            UserDomain userDomain = CommonUtilsSession.getUser(httpServletRequest);
            if(userDomain==null){
                return FreshServiceResult.createSuccessFreshServiceResult("用户未登录");
            } else {
                request.setUserId(userDomain.getUserId());
            }
            if(request.getFavoriteId()==null){
                return FreshServiceResult.createSuccessFreshServiceResult("喜欢的ID不能为空");
            }
            if(request.getFavoriteType()==null){
                return FreshServiceResult.createSuccessFreshServiceResult("喜欢的类型不能为空");
            }

            QueryUserFavoriteListRequest queryUserFavoriteListRequest = new QueryUserFavoriteListRequest();
            queryUserFavoriteListRequest.setUserId(request.getUserId());
            queryUserFavoriteListRequest.setFavoriteId(request.getFavoriteId());
            queryUserFavoriteListRequest.setFavoriteType(request.getFavoriteType());
            PageInformation<BookDTO> userFavoriteDtoList = userFavoriteService.queryUserFavoriteList(queryUserFavoriteListRequest);
            if(userFavoriteDtoList!=null&&userFavoriteDtoList.getTotalDataCount()>0){
                return FreshServiceResult.createSuccessFreshServiceResult("已经收藏");
            }
            userFavoriteService.addUserFavorite(request);
            return FreshServiceResult.createSuccessFreshServiceResult("添加收藏成功");
        } catch (Exception e){
            String message = "添加收藏失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ResponseBody
    @PostMapping("/PhysicsDeleteUserFavorite")
    public FreshServiceResult physicsDeleteUserFavorite(@RequestBody PhysicsDeleteUserFavoriteRequest request, HttpServletRequest httpServletRequest){
        try{
            UserDomain userDomain = CommonUtilsSession.getUser(httpServletRequest);
            if(userDomain==null){
                return FreshServiceResult.createSuccessFreshServiceResult("用户未登录");
            } else {
                request.setUserId(userDomain.getUserId());
            }
            if(request.getFavoriteId()==null){
                return FreshServiceResult.createSuccessFreshServiceResult("喜欢的ID不能为空");
            }
            if(request.getFavoriteType()==null){
                return FreshServiceResult.createSuccessFreshServiceResult("喜欢的类型不能为空");
            }
            userFavoriteService.physicsDeleteUserFavorite(request);
            return FreshServiceResult.createSuccessFreshServiceResult("删除收藏成功");
        } catch (Exception e){
            String message = "删除收藏失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ResponseBody
    @PostMapping("/QueryUserFavoriteBookList")
    public ServiceResult<QueryUserFavoriteBookListResponse> queryUserFavoriteList(@RequestBody QueryUserFavoriteListRequest request, HttpServletRequest httpServletRequest){
        try{
            UserDomain userDomain = CommonUtilsSession.getUser(httpServletRequest);
            request.setUserId(userDomain.getUserId());
            request.setFavoriteType(FavoriteType.BOOK.name());

            PageInformation<BookDTO> userFavoriteBookDtoList = userFavoriteService.queryUserFavoriteList(request);

            QueryUserFavoriteBookListResponse queryUserFavoriteListResponse = new QueryUserFavoriteBookListResponse();
            queryUserFavoriteListResponse.setBookDTOList(userFavoriteBookDtoList);
            return ServiceResult.createSuccessServiceResult("获取收藏列表成功",queryUserFavoriteListResponse);
        } catch (Exception e){
            String message = "获取收藏列表失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }
}
