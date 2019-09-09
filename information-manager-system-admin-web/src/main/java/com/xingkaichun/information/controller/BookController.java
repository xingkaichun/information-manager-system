package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.response.AddBookResponse;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.response.QueryBookListResponse;
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
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import com.xingkaichun.information.dto.bookSection.request.AddBookSectionRequest;
import com.xingkaichun.information.dto.bookSection.request.PhysicsDeleteBookSectionByBookSectionIdRequest;
import com.xingkaichun.information.dto.bookSection.request.QueryBookSectionListBybookChapterIdRequest;
import com.xingkaichun.information.dto.bookSection.request.UpdateBookSectionRequest;
import com.xingkaichun.information.dto.bookSection.response.AddBookSectionResponse;
import com.xingkaichun.information.dto.bookSection.response.QueryBookSectionListBybookChapterIdResponse;
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
            return ServiceResult.createSuccessServiceResult("查询书籍章节列表成功",response);
        } catch (Exception e){
            String message = "查询书籍章节列表失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }








    @ApiOperation(value="新增书籍小节", notes="新增书籍小节")
    @ResponseBody
    @PostMapping("/AddBookSection")
    public ServiceResult<AddBookSectionResponse> addBookSection(@RequestBody AddBookSectionRequest request, HttpServletRequest httpServletRequest){
        try{
            ServiceResult<BookSectionDTO> serviceResult = bookSectionService.addBookSection(httpServletRequest,request);
            if(!ServiceResult.isSuccess(serviceResult)){
                return FreshServiceResult.createFailFreshServiceResult(serviceResult.getMessage());
            }
            AddBookSectionResponse response = new AddBookSectionResponse();
            response.setBookSectionDTO(serviceResult.getResult());
            return ServiceResult.createSuccessServiceResult("新增书籍小节成功",response);
        } catch (Exception e){
            String message = "新增书籍小节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="更新书籍小节", notes="更新书籍小节")
    @ResponseBody
    @PostMapping("/UpdateBookSection")
    public FreshServiceResult updateBookSection(@RequestBody UpdateBookSectionRequest request){
        try{
            FreshServiceResult serviceResult = bookSectionService.updateBookSection(request);
            return serviceResult;
        } catch (Exception e){
            String message = "更新书籍小节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="物理删除书籍小节", notes="物理删除书籍小节")
    @ResponseBody
    @PostMapping("/PhysicsDeleteBookSectionByBookSectionId")
    public FreshServiceResult physicsDeleteBookSectionByBookSectionId(@RequestBody PhysicsDeleteBookSectionByBookSectionIdRequest request){
        try{
            FreshServiceResult freshServiceResult = bookSectionService.physicsDeleteBookSectionByBookSectionId(request);
            return freshServiceResult;
        } catch (Exception e){
            String message = "物理删除书籍小节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="查询书籍小节", notes="查询书籍小节")
    @ResponseBody
    @PostMapping("/QueryBookSectionListBybookChapterId")
    public ServiceResult<QueryBookSectionListBybookChapterIdResponse> queryBookSectionListBybookChapterId(@RequestBody QueryBookSectionListBybookChapterIdRequest request){
        try{
            ServiceResult<List<BookSectionDTO>> serviceResult = bookSectionService.queryBookSectionListBybookChapterId(request);
            if(!ServiceResult.isSuccess(serviceResult)){
                return FreshServiceResult.createFailFreshServiceResult(serviceResult.getMessage());
            }
            QueryBookSectionListBybookChapterIdResponse response = new QueryBookSectionListBybookChapterIdResponse();
            response.setBookSectionDTOList(serviceResult.getResult());
            return ServiceResult.createSuccessServiceResult("查询书籍小节列表成功",response);
        } catch (Exception e){
            String message = "查询书籍小节列表失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }


}
