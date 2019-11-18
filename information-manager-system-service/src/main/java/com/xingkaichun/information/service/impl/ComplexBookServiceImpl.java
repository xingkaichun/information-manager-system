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

        List<BookChapterDTO> bookChapterDTOList = bookDTO.getBookChapterDTOList();

        boolean finishCreateHomePage = false;
        String homePageContent = "";
        String homePageMulu = "";
        //生成小节HTML
        if(bookChapterDTOList!=null){
            for(BookChapterDTO bookChapterDTO:bookChapterDTOList){
                List<BookSectionDTO> bookSectionDTOList = bookChapterDTO.getBookSectionDTOList();
                if(bookSectionDTOList == null){
                    continue;
                }
                for(BookSectionDTO currentBookSectionDTO:bookSectionDTOList){

                    String content = CommonUtilsFile.readFileContent(bookTemplateFilePath);
                    String bookUrl = "/jiaocheng/"+bookDTO.getSeoUrl()+".html";
                    content = content.replace("[###BookName###]","<a href='"+bookUrl+"' class='link_li'><b>"+bookDTO.getBookName()+"</b></a>"+"\r\n");
                    if(!finishCreateHomePage){
                        homePageContent = content;
                    }
                    //生成目录
                    String mulu = createChapterHtml(bookDTO,currentBookSectionDTO);
                    content = content.replace("[###BookTableOfContents###]",mulu);
                    if(!finishCreateHomePage){
                        homePageMulu = createChapterHtml(bookDTO,null);
                        homePageContent = homePageContent.replace("[###BookTableOfContents###]",homePageMulu);
                    }
                    String bookSetionHtml = content;

                    BookSectionDTO previousBookSectionDTO = previousBookSectionDTO(currentBookSectionDTO);
                    String previousPage = previousBookSectionDTO==null?
                            "已经是第一章节了":
                            "<a href=\""+"/jiaocheng/"+bookDTO.getSeoUrl()+"/"+previousBookSectionDTO.getSeoUrl()+".html"+"\">"+"上一章:"+previousBookSectionDTO.getBookSectionName()+"</a>";

                    String previousPageHomePage = "";
                    if(!finishCreateHomePage){
                        previousPageHomePage = "已经是第一章节了";
                        previousPage = "<a href=\""+"/jiaocheng/"+bookDTO.getSeoUrl()+".html"+"\">"+"上一章:"+bookDTO.getBookName()+"简介"+"</a>";
                    }

                    BookSectionDTO nextBookSectionDTO = nextBookSectionDTO(currentBookSectionDTO);
                    String nextPage = nextBookSectionDTO==null?
                            "已经是最后章节了":
                            "<a href=\""+"/jiaocheng/"+bookDTO.getSeoUrl()+"/"+nextBookSectionDTO.getSeoUrl()+".html"+"\">"+"下一章:"+nextBookSectionDTO.getBookSectionName()+"</a>";

                    String nextPageHomePage = "";
                    if(!finishCreateHomePage){
                        nextPageHomePage = "<a href=\""+"/jiaocheng/"+bookDTO.getSeoUrl()+"/"+currentBookSectionDTO.getSeoUrl()+".html"+"\">"+"下一章:"+currentBookSectionDTO.getBookSectionName()+"</a>";
                    }


                    bookSetionHtml = bookSetionHtml.replace("[###SeoTitle###]",currentBookSectionDTO.getSeoTitle()+"_"+bookDTO.getBookName())
                                                    .replace("[###SeoKeywords###]",currentBookSectionDTO.getSeoKeywords())
                                                    .replace("[###SeoDescription###]",currentBookSectionDTO.getSeoDescription())
                                                    .replace("[###BookSectionName###]",currentBookSectionDTO.getBookSectionName()+"_"+bookDTO.getBookName())
                                                    .replace("[###BookSectionContent###]",currentBookSectionDTO.getBookSectionContent())
                                                    .replace("[###上一页###]",previousPage)
                                                    .replace("[###下一页###]",nextPage)
                                                    ;
                    File jiaochengDir = new File(bookTemplateProduceFileSaveDirectory,"jiaocheng");
                    File bookDir = new File(jiaochengDir,bookDTO.getSeoUrl());
                    CommonUtilsFile.writeFileContent(bookDir.getAbsolutePath(),currentBookSectionDTO.getSeoUrl()+".html",bookSetionHtml);
                    if(!finishCreateHomePage){
                        homePageContent = homePageContent.replace("[###SeoTitle###]",bookDTO.getSeoTitle()+"_"+bookDTO.getBookName())
                                .replace("[###SeoKeywords###]",bookDTO.getSeoKeywords())
                                .replace("[###SeoDescription###]",bookDTO.getSeoDescription())
                                .replace("[###BookSectionName###]",bookDTO.getBookName())
                                .replace("[###BookSectionContent###]",bookDTO.getBookDescription())
                                .replace("[###上一页###]",previousPageHomePage)
                                .replace("[###下一页###]",nextPageHomePage)
                                ;
                        File jiaochengHomePageDir = new File(bookTemplateProduceFileSaveDirectory,"jiaocheng");
                        CommonUtilsFile.writeFileContent(jiaochengHomePageDir.getAbsolutePath(),bookDTO.getSeoUrl()+".html",homePageContent);
                        finishCreateHomePage = true;
                    }
                }
            }
        }
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
                    String sectionUrl = "/jiaocheng/"+bookDTO.getSeoUrl()+"/"+bookSectionDTO2.getSeoUrl()+".html";
                    mulu += "       <dd><a href=\""+sectionUrl+"\" "+sectionCssClass+">"+bookSectionDTO2.getBookSectionName()+"</a></dd>"+"\r\n";
                }
            }
            mulu += "</dl>"+"\r\n";
        }
        return mulu;
    }
}
