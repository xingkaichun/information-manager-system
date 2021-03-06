package com.xingkaichun.information.service.impl;

import com.xingkaichun.common.dto.base.ServiceCode;
import com.xingkaichun.common.dto.base.ServiceResult;
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
        BookDTO bookDTO = bookService.queryBookByBookId(request.getBookId());
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
        if(bookId == null || "".equals(bookId.trim())){
            throw new RuntimeException("bookId不能为空");
        }

        QueryBookDetailsByBookIdRequest queryBookDetailsByBookIdRequest = new QueryBookDetailsByBookIdRequest();
        queryBookDetailsByBookIdRequest.setBookId(bookId);
        BookDTO bookDTO = queryBookDetailsByBookIdRequest(queryBookDetailsByBookIdRequest);
        List<BookChapterDTO> bookChapterDTOList = bookDTO.getBookChapterDTOList();

        File jiaochengHomePageDir = new File(new File(bookTemplateProduceFileSaveDirectory,"jiaocheng"),String.valueOf(bookDTO.getId()));
        String homePageHtml = "<!DOCTYPE html><head><meta charset=\"UTF-8\"></head><body><h1>书籍不完整，没有具体的章节内容，请尽快补充<h1></body>";
        CommonUtilsFile.writeFileContent(jiaochengHomePageDir.getAbsolutePath(),bookDTO.getSeoUrl()+".html",homePageHtml);

        boolean finishCreateHomePage = false;
        homePageHtml = "";
        //生成小节HTML
        if(bookChapterDTOList!=null){
            for(BookChapterDTO bookChapterDTO:bookChapterDTOList){
                List<BookSectionDTO> bookSectionDTOList = bookChapterDTO.getBookSectionDTOList();
                if(bookSectionDTOList == null){
                    continue;
                }
                for(BookSectionDTO currentBookSectionDTO:bookSectionDTOList){

                    String bookSetionHtml = CommonUtilsFile.readFileContent(bookTemplateFilePath);
                    String bookUrl = "/jiaocheng/"+bookDTO.getId()+"/"+bookDTO.getSeoUrl()+".html";
                    //左侧目录 书籍名称
                    String bookName = "<a href='"+bookUrl+"' class='link_li'><b>"+bookDTO.getBookName()+"</b></a>"+"\r\n";

                    if(!finishCreateHomePage){
                        homePageHtml = bookSetionHtml;
                    }

                    //左侧目录
                    String bookTableOfContents = createChapterHtml(bookDTO,currentBookSectionDTO);
                    String bookTableOfContentsHomePage = "";
                    if(!finishCreateHomePage){
                        bookTableOfContentsHomePage = createChapterHtml(bookDTO,null);
                    }

                    //上一章节链接
                    BookSectionDTO previousBookSectionDTO = previousBookSectionDTO(currentBookSectionDTO);
                    String previousPage = previousBookSectionDTO==null?
                            "<a id=\"prev\" href=\"javascript:void(0)\">"+"已经是第一章节了"+"</a>":
                            "<a id=\"prev\" href=\""+"/jiaocheng/"+bookDTO.getId()+"/"+bookDTO.getSeoUrl()+"/"+previousBookSectionDTO.getId()+"/"+previousBookSectionDTO.getSeoUrl()+".html"+"\">"+"上一章" /*: +previousBookSectionDTO.getBookSectionName() */ +"</a>";
                    String previousPageHomePage = "";
                    if(!finishCreateHomePage){
                        previousPageHomePage = "<a id=\"prev\" href=\"javascript:void(0)\">"+"已经是第一章节了"+"</a>";
                        previousPage = "<a id=\"prev\" href=\""+"/jiaocheng/"+bookDTO.getId()+"/"+bookDTO.getSeoUrl()+".html"+"\">"+"上一章"/*: +bookDTO.getBookName()+"简介"*/+"</a>";
                    }

                    //下一章节链接
                    BookSectionDTO nextBookSectionDTO = nextBookSectionDTO(currentBookSectionDTO);
                    String nextPage = nextBookSectionDTO==null?
                            "<a id=\"next\" href=\"javascript:void(0)\">"+"已经是最后章节了"+"</a>":
                            "<a id=\"next\" href=\""+"/jiaocheng/"+bookDTO.getId()+"/"+bookDTO.getSeoUrl()+"/"+nextBookSectionDTO.getId()+"/"+nextBookSectionDTO.getSeoUrl()+".html"+"\">"+"下一章" /* : +nextBookSectionDTO.getBookSectionName() */ +"</a>";
                    String nextPageHomePage = "";
                    if(!finishCreateHomePage){
                        nextPageHomePage = "<a id=\"next\" href=\""+"/jiaocheng/"+bookDTO.getId()+"/"+bookDTO.getSeoUrl()+"/"+currentBookSectionDTO.getId()+"/"+currentBookSectionDTO.getSeoUrl()+".html"+"\">"+"下一章" /* : +currentBookSectionDTO.getBookSectionName()*/ +"</a>";
                    }


                    bookSetionHtml = bookSetionHtml.replace("[###SeoTitle###]",currentBookSectionDTO.getSeoTitle()+"_"+bookDTO.getBookName())
                                                    .replace("[###SeoKeywords###]",currentBookSectionDTO.getSeoKeywords())
                                                    .replace("[###SeoDescription###]",currentBookSectionDTO.getSeoDescription())
                                                    .replace("[###BookSectionName###]",currentBookSectionDTO.getBookSectionName()+"_"+bookDTO.getBookName())
                                                    .replace("[###BookSectionContent###]",currentBookSectionDTO.getBookSectionContent())
                                                    .replace("[###上一页###]",previousPage)
                                                    .replace("[###下一页###]",nextPage)
                                                    .replace("[###BookTableOfContents###]",bookTableOfContents)
                                                    .replace("[###BookName###]",bookName)
                                                    ;
                    File jiaochengDir = new File(new File(bookTemplateProduceFileSaveDirectory,"jiaocheng"),String.valueOf(bookDTO.getId()));
                    jiaochengDir = new File(jiaochengDir,bookDTO.getSeoUrl());
                    File sectionDir = new File(jiaochengDir.getAbsolutePath(),String.valueOf(currentBookSectionDTO.getId()));
                    CommonUtilsFile.writeFileContent(sectionDir.getAbsolutePath(),currentBookSectionDTO.getSeoUrl()+".html",bookSetionHtml);
                    if(!finishCreateHomePage){
                        homePageHtml = homePageHtml.replace("[###SeoTitle###]",bookDTO.getSeoTitle()+"_"+bookDTO.getBookName())
                                .replace("[###SeoKeywords###]",bookDTO.getSeoKeywords())
                                .replace("[###SeoDescription###]",bookDTO.getSeoDescription())
                                .replace("[###BookSectionName###]",bookDTO.getBookName())
                                .replace("[###BookSectionContent###]",bookDTO.getBookDescription())
                                .replace("[###上一页###]",previousPageHomePage)
                                .replace("[###下一页###]",nextPageHomePage)
                                .replace("[###BookTableOfContents###]",bookTableOfContentsHomePage)
                                .replace("[###BookName###]",bookName)
                                ;
                        CommonUtilsFile.writeFileContent(jiaochengHomePageDir.getAbsolutePath(),bookDTO.getSeoUrl()+".html",homePageHtml);
                        finishCreateHomePage = true;
                    }
                }
            }
        }
    }

    @Override
    public void deleteHtmlPage(String bookId) throws Exception {
        if(bookId == null || "".equals(bookId.trim())){
            throw new RuntimeException("bookId不能为空");
        }

        QueryBookDetailsByBookIdRequest queryBookDetailsByBookIdRequest = new QueryBookDetailsByBookIdRequest();
        queryBookDetailsByBookIdRequest.setBookId(bookId);
        BookDTO bookDTO = queryBookDetailsByBookIdRequest(queryBookDetailsByBookIdRequest);

        if(bookDTO == null){
            throw new RuntimeException("没有查询到书籍，无法进行删除Html操作。");
        }

        File jiaochengDirectory = new File(new File(bookTemplateProduceFileSaveDirectory,"jiaocheng"),String.valueOf(bookDTO.getId()));
        CommonUtilsFile.deleteFile(jiaochengDirectory);
    }

    @Override
    public ServiceResult<String> getSectionPageUrlByBookSectionId(String bookSectionId) {
        BookSectionDTO bookSectionDTO = bookSectionService.queryBookSectionDTOBySectionId(bookSectionId);
        if(bookSectionDTO == null){
            return ServiceResult.createFailServiceResult("数据库中没有查询到该小节");
        }
        BookChapterDTO bookChapterDTO = bookChapterService.queryBookChapterByBookChapterId(bookSectionDTO.getBookChapterId());
        if(bookChapterDTO == null){
            return ServiceResult.createFailServiceResult("数据库中没有查询到该小节对应的章");
        }
        BookDTO bookDTO = bookService.queryBookByBookId(bookSectionDTO.getBookId());
        if(bookDTO == null){
            return ServiceResult.createFailServiceResult("数据库中没有查询到该小节对应的书籍");
        }
        String sectionPageUrl = "/jiaocheng/"+bookDTO.getId()+"/"+bookDTO.getSeoUrl()+"/"+bookSectionDTO.getId()+"/"+bookSectionDTO.getSeoUrl()+".html";
        return ServiceResult.createSuccessServiceResult("获取小节URL成功",sectionPageUrl);
    }





    private BookSectionDTO previousBookSectionDTO(BookSectionDTO currentBookSectionDTO) {
        return bookSectionService.previousBookSectionDTO(currentBookSectionDTO);
    }

    private BookSectionDTO nextBookSectionDTO(BookSectionDTO currentBookSectionDTO) {
        return bookSectionService.nextBookSectionDTO(currentBookSectionDTO);
    }

    private String createChapterHtml(BookDTO bookDTO,BookSectionDTO currentBookSectionDTO) {
        String mulu = "" ;
        List<BookChapterDTO> bookChapterDTOList = bookDTO.getBookChapterDTOList();
        for(BookChapterDTO bookChapterDTO:bookChapterDTOList){
            mulu += "<dl class=\"menu_left_list\">"+"\r\n";
            mulu += "   <dt onclick=\"flexMenu(event)\">"+bookChapterDTO.getBookChapterName()+"<img src=\"/images/arrows.png\" alt=\"\"></dt>"+"\r\n";

            List<BookSectionDTO> bookSectionDTOList2 = bookChapterDTO.getBookSectionDTOList();
            if(bookSectionDTOList2 != null){
                for(BookSectionDTO bookSectionDTO2:bookSectionDTOList2){
                    boolean isCurrentSection = false;
                    if(currentBookSectionDTO != null){
                        isCurrentSection = currentBookSectionDTO.getBookSectionId().equals(bookSectionDTO2.getBookSectionId());
                    }
                    String sectionCssClass = isCurrentSection?" class=\"on\" ":"";
                    String sectionUrl = "/jiaocheng/"+bookDTO.getId()+"/"+bookDTO.getSeoUrl()+"/"+bookSectionDTO2.getId()+"/"+bookSectionDTO2.getSeoUrl()+".html";
                    mulu += "       <dd><a href=\""+sectionUrl+"\" "+sectionCssClass+">"+bookSectionDTO2.getBookSectionName()+"</a></dd>"+"\r\n";
                }
            }
            mulu += "</dl>"+"\r\n";
        }
        return mulu;
    }
}
