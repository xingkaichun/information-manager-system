package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.AddBookResponse;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.QueryBookListResponse;
import com.xingkaichun.information.dto.book.request.AddBookRequest;
import com.xingkaichun.information.dto.book.request.PhysicsDeleteBookByBookIdRequest;
import com.xingkaichun.information.dto.book.request.QueryBookListRequest;
import com.xingkaichun.information.dto.book.request.UpdateBookRequest;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import com.xingkaichun.information.dto.bookChapter.request.AddBookChapterRequest;
import com.xingkaichun.information.dto.bookChapter.request.PhysicsDeleteBookChapterByBookChapterIdRequest;
import com.xingkaichun.information.dto.bookChapter.request.QueryBookChapterListByBookIdRequest;
import com.xingkaichun.information.dto.bookChapter.request.UpdateBookChapterRequest;
import com.xingkaichun.information.dto.bookChapter.response.AddBookChapterResponse;
import com.xingkaichun.information.dto.bookChapter.response.QueryBookChapterListByBookIdResponse;
import com.xingkaichun.information.service.BookChapterService;
import com.xingkaichun.information.service.BookSectionService;
import com.xingkaichun.information.service.BookService;
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
import java.util.List;

@Api(value="书籍controller",tags={"书籍操作接口"})
@Controller
@RequestMapping(value = "/Book")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private BookChapterService bookChapterService;
    @Autowired
    private BookSectionService bookSectionService;

    @ApiOperation(value="新增书籍", notes="新增书籍")
    @ResponseBody
    @PostMapping("/Addbook")
    public ServiceResult<AddBookResponse> addArticle(@RequestBody AddBookRequest request, HttpServletRequest httpServletRequest){
        try{
            ServiceResult<BookDTO> serviceResult = bookService.addBook(httpServletRequest,request);
            if(!ServiceResult.isSuccess(serviceResult)){
                return FreshServiceResult.createFailFreshServiceResult(serviceResult.getMessage());
            }
            AddBookResponse response = new AddBookResponse();
            response.setBookDTO(serviceResult.getResult());
            return ServiceResult.createSuccessServiceResult("新增书籍成功",response);
        } catch (Exception e){
            String message = "新增书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="更新书籍", notes="更新书籍")
    @ResponseBody
    @PostMapping("/UpdateBook")
    public FreshServiceResult updateBook(@RequestBody UpdateBookRequest request){
        try{
            FreshServiceResult freshServiceResult = bookService.updateBook(request);
            return freshServiceResult;
        } catch (Exception e){
            String message = "更新书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="物理删除书籍", notes="物理删除书籍")
    @ResponseBody
    @PostMapping("/PhysicsDeleteBookByBookId")
    public FreshServiceResult physicsDeleteBookByBookId(@RequestBody PhysicsDeleteBookByBookIdRequest request){
        try{
            FreshServiceResult freshServiceResult = bookService.physicsDeleteBookByBookId(request);
            return freshServiceResult;
        } catch (Exception e){
            String message = "物理删除书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="查询全部书籍", notes="查询全部书籍")
    @ResponseBody
    @PostMapping("/QueryBookList")
    public ServiceResult<QueryBookListResponse> queryBookList(@RequestBody QueryBookListRequest request){
        try{
            ServiceResult<List<BookDTO>> serviceResult = bookService.queryBookList(request);
            if(!ServiceResult.isSuccess(serviceResult)){
                return FreshServiceResult.createFailFreshServiceResult(serviceResult.getMessage());
            }
            QueryBookListResponse response = new QueryBookListResponse();
            response.setBookDTOList(serviceResult.getResult());
            return ServiceResult.createSuccessServiceResult("新增书籍成功",response);
        } catch (Exception e){
            String message = "物理删除失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }




    @ApiOperation(value="新增书籍章节", notes="新增书籍章节")
    @ResponseBody
    @PostMapping("/AddBookChapter")
    public ServiceResult<AddBookChapterResponse> addBookChapter(@RequestBody AddBookChapterRequest request){
        try{
            ServiceResult<BookChapterDTO> serviceResult = bookChapterService.addBookChapter(request);
            if(!ServiceResult.isSuccess(serviceResult)){
                return FreshServiceResult.createFailFreshServiceResult(serviceResult.getMessage());
            }
            AddBookChapterResponse response = new AddBookChapterResponse();
            response.setBookChapterDTO(serviceResult.getResult());
            return ServiceResult.createSuccessServiceResult("新增书籍章节成功",response);
        } catch (Exception e){
            String message = "新增书籍章节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="更新书籍章节", notes="更新书籍章节")
    @ResponseBody
    @PostMapping("/UpdateBookChapter")
    public FreshServiceResult updateBookChapter(@RequestBody UpdateBookChapterRequest request){
        try{
            FreshServiceResult serviceResult = bookChapterService.updateBookChapter(request);
            return serviceResult;
        } catch (Exception e){
            String message = "更新书籍章节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="物理删除书籍章节", notes="物理删除书籍章节")
    @ResponseBody
    @PostMapping("/PhysicsDeleteBookChapterByBookChapterId")
    public FreshServiceResult physicsDeleteBookChapterByBookChapterId(@RequestBody PhysicsDeleteBookChapterByBookChapterIdRequest request){
        try{
            FreshServiceResult freshServiceResult = bookChapterService.physicsDeleteBookChapterByBookChapterId(request);
            return freshServiceResult;
        } catch (Exception e){
            String message = "物理删除书籍章节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="查询书籍章节", notes="查询书籍章节")
    @ResponseBody
    @PostMapping("/QueryBookChapterListByBookId")
    public ServiceResult<QueryBookChapterListByBookIdResponse> queryBookChapterListByBookId(@RequestBody QueryBookChapterListByBookIdRequest request){
        try{
            ServiceResult<List<BookChapterDTO>> serviceResult = bookChapterService.queryBookChapterListByBookId(request);
            if(!ServiceResult.isSuccess(serviceResult)){
                return FreshServiceResult.createFailFreshServiceResult(serviceResult.getMessage());
            }
            QueryBookChapterListByBookIdResponse response = new QueryBookChapterListByBookIdResponse();
            response.setBookChapterDTOList(serviceResult.getResult());
            return ServiceResult.createSuccessServiceResult("新增书籍成功",response);
        } catch (Exception e){
            String message = "查询书籍章节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }
}
