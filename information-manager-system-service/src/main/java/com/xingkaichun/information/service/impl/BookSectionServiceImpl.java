package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BookChapterDao;
import com.xingkaichun.information.dao.BookDao;
import com.xingkaichun.information.dao.BookSectionDao;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.request.UpdateBookRequest;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import com.xingkaichun.information.dto.bookSection.request.AddBookSectionRequest;
import com.xingkaichun.information.dto.bookSection.request.PhysicsDeleteBookSectionByBookSectionIdRequest;
import com.xingkaichun.information.dto.bookSection.request.QueryBookSectionListBybookChapterIdRequest;
import com.xingkaichun.information.dto.bookSection.request.UpdateBookSectionRequest;
import com.xingkaichun.information.model.BookChapterDomain;
import com.xingkaichun.information.model.BookSectionDomian;
import com.xingkaichun.information.service.BookSectionService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "bookSectionServiceImpl")
public class BookSectionServiceImpl implements BookSectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookSectionServiceImpl.class);

    @Autowired
    private BookSectionDao bookSectionDao;
    @Autowired
    private BookChapterDao bookChapterDao;
    @Autowired
    private BookDao bookDao;

    @Override
    public ServiceResult<BookSectionDTO> addBookSection(HttpServletRequest httpServletRequest, AddBookSectionRequest request) {
        try{
            if(!CommonUtils.isNUllOrEmpty(request.getBookId())){
                return FreshServiceResult.createFailFreshServiceResult("请勿填写书籍ID，书籍ID由系统自动寻找");
            }
            if(CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节ID不能为空");
            } else {
                //校验书籍章节存在
                BookChapterDomain bookChapterDomain = bookChapterDao.queryBookChapterByBookChapterId(request.getBookChapterId());
                if(bookChapterDomain == null){
                    return FreshServiceResult.createFailFreshServiceResult("书籍章节不存在，无法添加书籍小节");
                }
                request.setBookId(bookChapterDomain.getBookId());
            }
            if(CommonUtils.isNUllOrEmpty(request.getBookSectionName())){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节名称不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(request.getBookSectionId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配书籍小节ID，请不要填写书籍小节ID");
            } else {
                request.setBookSectionId(String.valueOf(UUID.randomUUID()));
            }

            if(CommonUtils.isNUllOrEmpty(request.getBookSectionOrder())){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节排序值不能为空");
            }
            request.setBookSectionContent(CommonUtilsHtml.handlerArticleContent(request.getBookSectionContent()));
            BookSectionDomian bookDomain = classCast2(request);
            bookSectionDao.addBookSection(bookDomain);

            //更新book时间戳
            UpdateBookRequest updateBookRequest = new UpdateBookRequest();
            updateBookRequest.setBookId(request.getBookId());
            bookDao.updateBook(updateBookRequest);

            BookSectionDomian bookSectionDomian = bookSectionDao.queryBookSectionByBookSectionId(request.getBookSectionId());
            BookSectionDTO bookSectionDTO = classCast(bookSectionDomian);
            return FreshServiceResult.createSuccessServiceResult("新增书籍小节成功",bookSectionDTO);
        } catch (Exception e){
            String message = "新增书籍小节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult updateBookSection(UpdateBookSectionRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookSectionId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节ID不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(request.getBookId())){
                return FreshServiceResult.createFailFreshServiceResult("不允许更改书籍ID");
            }
            if(!CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("不允许更改书籍章节ID");
            }
            BookSectionDomian bookSectionDomian = bookSectionDao.queryBookSectionByBookSectionId(request.getBookSectionId());
            if(bookSectionDomian == null){
                return FreshServiceResult.createSuccessFreshServiceResult("书籍小节不存在");
            }
            request.setBookSectionContent(CommonUtilsHtml.handlerArticleContent(request.getBookSectionContent()));
            bookSectionDao.updateBookSection(request);
            //更新book时间戳
            UpdateBookRequest updateBookRequest = new UpdateBookRequest();
            updateBookRequest.setBookId(bookSectionDomian.getBookId());
            bookDao.updateBook(updateBookRequest);

            return FreshServiceResult.createSuccessFreshServiceResult("更新书籍小节成功");
        } catch (Exception e){
            String message = "更新书籍小节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult physicsDeleteBookSectionByBookSectionId(PhysicsDeleteBookSectionByBookSectionIdRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookSectionId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节ID不能为空");
            }
            //校验软删除标识
            BookSectionDomian bookSectionDomian = bookSectionDao.queryBookSectionByBookSectionId(request.getBookSectionId());
            if(bookSectionDomian==null){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节不存在");
            }
            if(!bookSectionDomian.isSoftDelete()){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节软删除标识为不可删");
            }
            bookSectionDao.physicsDeleteBookSectionByBookSectionId(request.getBookSectionId());

            //更新book时间戳
            UpdateBookRequest updateBookRequest = new UpdateBookRequest();
            updateBookRequest.setBookId(bookSectionDomian.getBookId());
            bookDao.updateBook(updateBookRequest);

            return FreshServiceResult.createSuccessFreshServiceResult("删除书籍小节成功");
        } catch (Exception e){
            String message = "删除书籍小节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public ServiceResult<List<BookSectionDTO>> queryBookSectionListBybookChapterId(QueryBookSectionListBybookChapterIdRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节ID不能为空");
            }

            List<BookSectionDomian> bookSectionDomianList = bookSectionDao.queryBookSectionListBybookChapterId(request.getBookChapterId());
            List<BookSectionDTO> bookDTOList = classCast(bookSectionDomianList);

            return FreshServiceResult.createSuccessServiceResult("查询书籍小节列表成功",bookDTOList);
        } catch (Exception e){
            String message = "查询书籍小节列表失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    private List<BookSectionDTO> classCast(List<BookSectionDomian> domainList) {
        if(CommonUtils.isNUllOrEmpty(domainList)){
            return null;
        }
        List<BookSectionDTO> dtoList = new ArrayList<>();
        for (BookSectionDomian domian:domainList){
            dtoList.add(classCast(domian));
        }
        return dtoList;
    }

    private BookSectionDTO classCast(BookSectionDomian domain) {
        BookSectionDTO dto = new BookSectionDTO();
        dto.setBookId(domain.getBookId());
        dto.setBookChapterId(domain.getBookChapterId());
        dto.setBookSectionId(domain.getBookSectionId());
        dto.setBookSectionName(domain.getBookSectionName());
        dto.setBookSectionDescription(domain.getBookSectionDescription());
        dto.setBookSectionContent(domain.getBookSectionContent());
        dto.setBookSectionOrder(domain.getBookSectionOrder());

        dto.setCreateTime(domain.getCreateTime());
        dto.setLastEditTime(domain.getLastEditTime());
        dto.setSoftDelete(domain.isSoftDelete());

        dto.setSeoUrl(domain.getSeoUrl());
        dto.setSeoTitle(domain.getSeoTitle());
        dto.setSeoKeywords(domain.getSeoKeywords());
        dto.setSeoDescription(domain.getSeoDescription());
        return dto;
    }

    private BookSectionDomian classCast2(AddBookSectionRequest addBookRequest) {
        return classCast(addBookRequest);
    }

    private BookSectionDomian classCast(BookSectionDTO dto) {
        BookSectionDomian domain = new BookSectionDomian();
        domain.setBookId(dto.getBookId());
        domain.setBookChapterId(dto.getBookChapterId());
        domain.setBookSectionId(dto.getBookSectionId());
        domain.setBookSectionName(dto.getBookSectionName());
        domain.setBookSectionDescription(dto.getBookSectionDescription());
        domain.setBookSectionContent(dto.getBookSectionContent());
        domain.setBookSectionOrder(dto.getBookSectionOrder());

        domain.setCreateTime(dto.getCreateTime());
        domain.setLastEditTime(dto.getLastEditTime());
        domain.setSoftDelete(dto.isSoftDelete());

        domain.setSeoUrl(dto.getSeoUrl());
        domain.setSeoTitle(dto.getSeoTitle());
        domain.setSeoKeywords(dto.getSeoKeywords());
        domain.setSeoDescription(dto.getSeoDescription());
        return domain;
    }

}