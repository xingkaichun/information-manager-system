package com.xingkaichun.information.controller;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.bookFavorite.request.AddFavoriteBookRequest;
import com.xingkaichun.information.dto.bookFavorite.request.PhysicsDeleteUserFavoriteRequest;
import com.xingkaichun.information.dto.bookFavorite.request.QueryUserFavoriteListRequest;
import com.xingkaichun.information.dto.bookFavorite.response.QueryUserFavoriteListResponse;
import com.xingkaichun.information.dto.favorite.UserFavoriteDto;
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
import java.util.ArrayList;
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
    @PostMapping("/AddFavoriteBook")
    public FreshServiceResult addFavoriteBook(@RequestBody AddFavoriteBookRequest request, HttpServletRequest httpServletRequest){
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
            request.setFavoriteType("book");

            QueryUserFavoriteListRequest queryUserFavoriteListRequest = new QueryUserFavoriteListRequest();
            queryUserFavoriteListRequest.setUserId(request.getUserId());
            queryUserFavoriteListRequest.setFavoriteId(request.getFavoriteId());
            queryUserFavoriteListRequest.setFavoriteType("book");
            List<UserFavoriteDto> userFavoriteDtoList = userFavoriteService.queryUserFavoriteList(queryUserFavoriteListRequest);
            if(userFavoriteDtoList!=null&&userFavoriteDtoList.size()>0){
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
    @PostMapping("/PhysicsDeleteUserFavoriteBook")
    public FreshServiceResult physicsDeleteUserFavorite(@RequestBody PhysicsDeleteUserFavoriteRequest request, HttpServletRequest httpServletRequest){
        try{
            UserDomain userDomain = CommonUtilsSession.getUser(httpServletRequest);
            request.setUserId(userDomain.getUserId());
            request.setFavoriteType("book");
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
    public ServiceResult<QueryUserFavoriteListResponse> queryUserFavoriteList(@RequestBody QueryUserFavoriteListRequest request, HttpServletRequest httpServletRequest){
        try{
            UserDomain userDomain = CommonUtilsSession.getUser(httpServletRequest);
            request.setUserId(userDomain.getUserId());
            request.setFavoriteType("book");

            List<UserFavoriteDto> userFavoriteDtoList = userFavoriteService.queryUserFavoriteList(request);
            if(userFavoriteDtoList==null||userFavoriteDtoList.size()==0){
                return ServiceResult.createSuccessServiceResult("获取收藏列表成功",null);
            }
            List<String> bookIds = new ArrayList<>();
            for(UserFavoriteDto userFavoriteDto:userFavoriteDtoList){
                bookIds.add(userFavoriteDto.getFavoriteId());
            }
            List<BookDTO> bookDTOList = bookService.queryBookListByBookIds(bookIds);

            QueryUserFavoriteListResponse queryUserFavoriteListResponse = new QueryUserFavoriteListResponse();
            queryUserFavoriteListResponse.setBookDTOList(bookDTOList);
            return ServiceResult.createSuccessServiceResult("获取收藏列表成功",queryUserFavoriteListResponse);
        } catch (Exception e){
            String message = "获取收藏列表失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }
}
