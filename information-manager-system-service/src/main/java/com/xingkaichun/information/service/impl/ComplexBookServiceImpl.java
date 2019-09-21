package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dto.base.ServiceCode;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.QueryBookDetailsByBookIdRequest;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import com.xingkaichun.information.dto.bookChapter.request.QueryBookChapterListByBookIdRequest;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import com.xingkaichun.information.dto.bookSection.request.QueryBookSectionListBybookChapterIdRequest;
import com.xingkaichun.information.service.BookChapterService;
import com.xingkaichun.information.service.BookSectionService;
import com.xingkaichun.information.service.BookService;
import com.xingkaichun.information.service.ComplexBookService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service(value = "complexBookService")
public class ComplexBookServiceImpl implements ComplexBookService {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookChapterService bookChapterService;
    @Autowired
    private BookSectionService bookSectionService;

    @Value("${project.template.bookTemplateFilePath}")
    public String bookTemplateFilePath;
    @Value("${project.template.bookTemplateProduceFileSaveDirectory}")
    public String bookTemplateProduceFileSaveDirectory;

    @Override
    public BookDTO queryBookDetailsByBookIdRequest(QueryBookDetailsByBookIdRequest request) {
        BookDTO bookDTO = bookService.queryBook(request);
        if(bookDTO==null){
            return null;
        }
        QueryBookChapterListByBookIdRequest queryBookChapterListByBookIdRequest = new QueryBookChapterListByBookIdRequest();
        queryBookChapterListByBookIdRequest.setBookId(bookDTO.getBookId());
        ServiceResult<List<BookChapterDTO>> serviceResult = bookChapterService.queryBookChapterListByBookId(queryBookChapterListByBookIdRequest);
        if(serviceResult.getServiceCode()!= ServiceCode.SUCCESS){
            return bookDTO;
        }
        List<BookChapterDTO> bookChapterDTOList = serviceResult.getResult();
        bookDTO.setBookChapterDTOList(bookChapterDTOList);

        if(!CommonUtils.isNUllOrEmpty(bookChapterDTOList)){
            for(BookChapterDTO bookChapterDTO:bookChapterDTOList){
                QueryBookSectionListBybookChapterIdRequest queryBookSectionListBybookChapterIdRequest = new QueryBookSectionListBybookChapterIdRequest();
                queryBookSectionListBybookChapterIdRequest.setBookChapterId(bookChapterDTO.getBookChapterId());
                ServiceResult<List<BookSectionDTO>> bookSectionServiceResult = bookSectionService.queryBookSectionListBybookChapterId(queryBookSectionListBybookChapterIdRequest);
                bookChapterDTO.setBookSectionDTOList(bookSectionServiceResult.getResult());
            }
        }
        return bookDTO;
    }

    @Override
    public void createHtmlPage(String bookId) throws IOException {
        QueryBookDetailsByBookIdRequest queryBookDetailsByBookIdRequest = new QueryBookDetailsByBookIdRequest();
        queryBookDetailsByBookIdRequest.setBookId(bookId);
        BookDTO bookDTO = queryBookDetailsByBookIdRequest(queryBookDetailsByBookIdRequest);

        String content = CommonUtilsFile.readFileContent(bookTemplateFilePath);
        content = content.replace("[###BookName###]","<a href='"+bookDTO.getSeoUrl()+"' class='link_li'><b>"+bookDTO.getBookName()+"</b></a>");
        String mulu = "" ;
        List<BookChapterDTO> bookChapterDTOList = bookDTO.getBookChapterDTOList();
        //生成目录
        if(bookChapterDTOList!=null){
            for(BookChapterDTO bookChapterDTO:bookChapterDTOList){
                mulu += "<a href='javascript:void(0)' class='link_li'>"+bookChapterDTO.getBookChapterName()+"</a>";
                List<BookSectionDTO> bookSectionDTOList = bookChapterDTO.getBookSectionDTOList();
                if(bookSectionDTOList == null){
                    continue;
                }
                for(BookSectionDTO bookSectionDTO:bookSectionDTOList){
                    mulu += "<a href='"+bookSectionDTO.getSeoUrl()+"' class='link_li'>"+bookSectionDTO.getBookSectionName()+"</a>";
                }
            }
        }
        content = content.replace("[###BookTableOfContents###]",mulu);
        //生成小节HTML
        if(bookChapterDTOList!=null){
            for(BookChapterDTO bookChapterDTO:bookChapterDTOList){
                List<BookSectionDTO> bookSectionDTOList = bookChapterDTO.getBookSectionDTOList();
                if(bookSectionDTOList == null){
                    continue;
                }
                for(BookSectionDTO bookSectionDTO:bookSectionDTOList){
                    String bookSetionHtml = content;
                    bookSetionHtml = bookSetionHtml.replace("[###SeoKeywords###]",bookSectionDTO.getSeoKeywords())
                                                    .replace("[###SeoDescription###]",bookSectionDTO.getSeoDescription())
                                                    .replace("[###BookSectionName###]",bookSectionDTO.getBookSectionName())
                                                    .replace("[###BookSectionContent###]",bookSectionDTO.getBookSectionContent());
                    File dir = new File(bookTemplateProduceFileSaveDirectory,bookDTO.getSeoUrl());
                    CommonUtilsFile.writeFileContent(dir.getAbsolutePath(),bookSectionDTO.getSeoUrl()+".html",bookSetionHtml);
                }
            }
        }
    }
}
