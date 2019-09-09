package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.AddBookRequest;
import com.xingkaichun.information.dto.book.request.PhysicsDeleteBookByBookIdRequest;
import com.xingkaichun.information.dto.book.request.UpdateBookRequest;
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
    public ServiceResult<BookDTO> addArticle(@RequestBody AddBookRequest request, HttpServletRequest httpServletRequest){
        try{
            ServiceResult<BookDTO> serviceResult = bookService.addBook(httpServletRequest,request);
            return serviceResult;
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
            String message = "物理删除失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="查询全部书籍", notes="查询全部书籍")
    @ResponseBody
    @PostMapping("/QueryBookList")
    public ServiceResult<List<BookDTO>> queryBookList(){
        try{
            ServiceResult<List<BookDTO>> serviceResult = bookService.queryBookList();
            return serviceResult;
        } catch (Exception e){
            String message = "物理删除失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }
}
