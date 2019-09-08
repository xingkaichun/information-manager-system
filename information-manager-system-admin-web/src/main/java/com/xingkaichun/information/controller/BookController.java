package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.book.request.AddBookRequest;
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

    @ApiOperation(value="新增文章", notes="新增文章")
    @ResponseBody
    @PostMapping("/Addbook")
    public FreshServiceResult addArticle(@RequestBody AddBookRequest addBookRequest, HttpServletRequest httpServletRequest){
        try{
            bookService.addBook(httpServletRequest,addBookRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("新增书籍成功");
        } catch (Exception e){
            String message = "新增书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

}
