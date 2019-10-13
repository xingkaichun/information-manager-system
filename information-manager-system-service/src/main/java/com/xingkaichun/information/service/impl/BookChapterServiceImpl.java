package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BookChapterDao;
import com.xingkaichun.information.dao.BookDao;
import com.xingkaichun.information.dao.BookSectionDao;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.request.UpdateBookRequest;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import com.xingkaichun.information.dto.bookChapter.request.AddBookChapterRequest;
import com.xingkaichun.information.dto.bookChapter.request.PhysicsDeleteBookChapterByBookChapterIdRequest;
import com.xingkaichun.information.dto.bookChapter.request.QueryBookChapterListByBookIdRequest;
import com.xingkaichun.information.dto.bookChapter.request.UpdateBookChapterRequest;
import com.xingkaichun.information.model.BookChapterDomain;
import com.xingkaichun.information.model.BookDomain;
import com.xingkaichun.information.model.BookSectionDomian;
import com.xingkaichun.information.service.BookChapterService;
import com.xingkaichun.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "bookChapterServiceImpl")
public class BookChapterServiceImpl implements BookChapterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookChapterServiceImpl.class);

    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookChapterDao bookChapterDao;
    @Autowired
    private BookSectionDao bookSectionDao;

    @Override
    public ServiceResult<BookChapterDTO> addBookChapter(AddBookChapterRequest request) {
        try{
            String bookId = request.getBookId();
            if(CommonUtils.isNUllOrEmpty(bookId)){
                return FreshServiceResult.createFailFreshServiceResult("书籍ID不能为空");
            } else {
                BookDomain bookDomain = bookDao.queryBookByBookId(bookId);
                if(bookDomain == null){
                    return FreshServiceResult.createFailFreshServiceResult("书籍不存在，无法添加章节");
                }
            }

            if(!CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配书籍章节ID，请不要填写书籍章节ID");
            } else {
                request.setBookChapterId(String.valueOf(UUID.randomUUID()));
            }

            if(CommonUtils.isNUllOrEmpty(request.getBookChapterName())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节名称不能为空");
            }

            if(CommonUtils.isNUllOrEmpty(request.getBookChapterOrder())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节排序值不能为空");
            }

            BookChapterDomain bookChapterDomain = classCast2(request);
            bookChapterDao.addBookChapter(bookChapterDomain);

            //更新book时间戳
            UpdateBookRequest updateBookRequest = new UpdateBookRequest();
            updateBookRequest.setBookId(bookId);
            bookDao.updateBook(updateBookRequest);

            BookChapterDomain dbBookChapterDomain = bookChapterDao.queryBookChapterByBookChapterId(request.getBookChapterId());
            BookChapterDTO bookChapterDTO = classCast(dbBookChapterDomain);
            return FreshServiceResult.createSuccessServiceResult("新增书籍章节成功",bookChapterDTO);
        } catch (Exception e){
            String message = "新增书籍章节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult updateBookChapter(UpdateBookChapterRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节ID不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(request.getBookId())){
                return FreshServiceResult.createFailFreshServiceResult("不允许更改书籍ID");
            }
            BookChapterDomain bookChapterDomain = bookChapterDao.queryBookChapterByBookChapterId(request.getBookChapterId());
            if(bookChapterDomain == null){
                return FreshServiceResult.createSuccessFreshServiceResult("书籍章节不存在");
            }
            bookChapterDao.updateBookChapter(request);
            //更新book时间戳
            UpdateBookRequest updateBookRequest = new UpdateBookRequest();
            updateBookRequest.setBookId(bookChapterDomain.getBookId());
            bookDao.updateBook(updateBookRequest);

            return FreshServiceResult.createSuccessFreshServiceResult("更新书籍章节成功");
        } catch (Exception e){
            String message = "更新书籍章节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult physicsDeleteBookChapterByBookChapterId(PhysicsDeleteBookChapterByBookChapterIdRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节ID不能为空");
            }
            //校验软删除标识
            BookChapterDomain bookChapterDomain = bookChapterDao.queryBookChapterByBookChapterId(request.getBookChapterId());
            if(bookChapterDomain==null){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节不存在");
            }
            if(!bookChapterDomain.isSoftDelete()){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节软删除标识为不可删");
            }
            //校验 章节下不能有小节
            List<BookSectionDomian> bookSectionDomianList = bookSectionDao.queryBookSectionListBybookChapterId(request.getBookChapterId());
            if(!CommonUtils.isNUllOrEmpty(bookSectionDomianList)){
                return FreshServiceResult.createFailFreshServiceResult("章节下不能有小节");
            }

            bookChapterDao.physicsDeleteBookChapterByBookChapterId(request.getBookChapterId());

            //更新book时间戳
            UpdateBookRequest updateBookRequest = new UpdateBookRequest();
            updateBookRequest.setBookId(bookChapterDomain.getBookId());
            bookDao.updateBook(updateBookRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("删除书籍章节成功");
        } catch (Exception e){
            String message = "删除书籍章节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public ServiceResult<List<BookChapterDTO>> queryBookChapterListByBookId(QueryBookChapterListByBookIdRequest request) {
        try{
            String bookId = request.getBookId();
            if(CommonUtils.isNUllOrEmpty(bookId)){
                return FreshServiceResult.createFailFreshServiceResult("书籍ID不能为空");
            }
            List<BookChapterDomain> BookChapterDomainList = bookChapterDao.queryBookChapterListByBookId(bookId);
            List<BookChapterDTO> bookChapterDTOList = classCast(BookChapterDomainList);

            return FreshServiceResult.createSuccessServiceResult("查询书籍章节列表成功",bookChapterDTOList);
        } catch (Exception e){
            String message = "查询书籍章节列表失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public BookChapterDTO queryBookChapterByBookChapterId(String bookChapterId) {
        BookChapterDomain bookChapterDomain = bookChapterDao.queryBookChapterByBookChapterId(bookChapterId);
        return classCast(bookChapterDomain);
    }

    private List<BookChapterDTO> classCast(List<BookChapterDomain> bookChapterDomainList) {
        if(CommonUtils.isNUllOrEmpty(bookChapterDomainList)){
            return null;
        }
        List<BookChapterDTO> dtoList = new ArrayList<>();
        for (BookChapterDomain domain:bookChapterDomainList){
            dtoList.add(classCast(domain));
        }
        return dtoList;
    }

    private BookChapterDTO classCast(BookChapterDomain domain) {
        BookChapterDTO dto = new BookChapterDTO();
        dto.setBookId(domain.getBookId());
        dto.setBookChapterId(domain.getBookChapterId());
        dto.setBookChapterName(domain.getBookChapterName());
        dto.setBookChapterDescription(domain.getBookChapterDescription());
        dto.setBookChapterOrder(domain.getBookChapterOrder());

        dto.setCreateTime(domain.getCreateTime());
        dto.setLastEditTime(domain.getLastEditTime());
        dto.setSoftDelete(domain.isSoftDelete());

        dto.setSeoUrl(domain.getSeoUrl());
        dto.setSeoTitle(domain.getSeoTitle());
        dto.setSeoKeywords(domain.getSeoKeywords());
        dto.setSeoDescription(domain.getSeoDescription());
        return dto;
    }

    private BookChapterDomain classCast2(AddBookChapterRequest addBookChapterRequest) {
        return classCast(addBookChapterRequest);
    }

    private BookChapterDomain classCast(BookChapterDTO dto) {
        BookChapterDomain domain = new BookChapterDomain();
        domain.setBookId(dto.getBookId());
        domain.setBookChapterId(dto.getBookChapterId());
        domain.setBookChapterName(dto.getBookChapterName());
        domain.setBookChapterDescription(dto.getBookChapterDescription());
        domain.setBookChapterOrder(dto.getBookChapterOrder());

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