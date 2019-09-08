package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BookChapterDao;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import com.xingkaichun.information.dto.bookChapter.request.AddBookChapterRequest;
import com.xingkaichun.information.dto.bookChapter.request.PhysicsDeleteBookChapterByBookChapterIdRequest;
import com.xingkaichun.information.dto.bookChapter.request.QueryBookChapterListByBookIdRequest;
import com.xingkaichun.information.dto.bookChapter.request.UpdateBookChapterRequest;
import com.xingkaichun.information.model.BookChapterDomain;
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
    private BookChapterDao bookChapterDao;

    @Override
    public FreshServiceResult addBookChapter(AddBookChapterRequest request) {
        try{
            String bookId = request.getBookId();
            if(CommonUtils.isNUllOrEmpty(bookId)){
                return FreshServiceResult.createFailFreshServiceResult("书籍ID不能为空");
            } else {
              //TODO 校验BookId存在
            }

            if(!CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配书籍章节ID，请不要填写书籍章节ID");
            } else {
                request.setBookChapterId(String.valueOf(UUID.randomUUID()));
            }

            if(CommonUtils.isNUllOrEmpty(request.getBookChapterName())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节名称不能为空");
            }

            BookChapterDomain bookChapterDomain = classCast2(request);
            bookChapterDao.addBookChapter(bookChapterDomain);
            return FreshServiceResult.createSuccessFreshServiceResult("新增书籍章节成功");
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
            bookChapterDao.updateBookChapter(request);
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
            //TODO 校验软删除标识
            //TODO 校验章节下不能有小节
            bookChapterDao.physicsDeleteBookChapterByBookChapterId(request.getBookChapterId());
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

            return FreshServiceResult.createSuccessServiceResult("查询书籍列表成功",bookChapterDTOList);
        } catch (Exception e){
            String message = "删除书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
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
        dto.setBookChapterOrder(domain.getBookChapterOrder());
        dto.setCreateTime(domain.getCreateTime());
        dto.setSoftDelete(domain.isSoftDelete());
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
        domain.setBookChapterOrder(dto.getBookChapterOrder());
        domain.setCreateTime(dto.getCreateTime());
        domain.setSoftDelete(dto.isSoftDelete());
        return domain;
    }
}
