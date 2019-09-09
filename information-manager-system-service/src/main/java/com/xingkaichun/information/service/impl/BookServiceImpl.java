package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BookChapterDao;
import com.xingkaichun.information.dao.BookDao;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.AddBookRequest;
import com.xingkaichun.information.dto.book.request.PhysicsDeleteBookByBookIdRequest;
import com.xingkaichun.information.dto.book.request.QueryBookListRequest;
import com.xingkaichun.information.dto.book.request.UpdateBookRequest;
import com.xingkaichun.information.model.BookChapterDomain;
import com.xingkaichun.information.model.BookDomain;
import com.xingkaichun.information.model.BookSectionDomian;
import com.xingkaichun.information.service.BookService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "bookService")
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookChapterDao bookChapterDao;


    @Override
    public ServiceResult<BookDTO> addBook(HttpServletRequest request, AddBookRequest addBookRequest) {
        try{
            addBookRequest.setAuthorId(CommonUtilsSession.getUser(request).getUserId());
            if(!CommonUtils.isNUllOrEmpty(addBookRequest.getBookId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配书籍ID，请不要填写书籍ID");
            } else {
                addBookRequest.setBookId(String.valueOf(UUID.randomUUID()));
            }
            if(CommonUtils.isNUllOrEmpty(addBookRequest.getBookName())){
                return FreshServiceResult.createFailFreshServiceResult("书籍名称不能为空");
            }
            BookDomain bookDomain = classCast2(addBookRequest);
            bookDao.addBook(bookDomain);

            BookDomain dbBookDomain = bookDao.queryBook(addBookRequest.getBookId());
            BookDTO bookDTO = classCast(dbBookDomain);
            return FreshServiceResult.createSuccessServiceResult("新增书籍成功",bookDTO);
        } catch (Exception e){
            String message = "新增书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult updateBook(UpdateBookRequest updateBookRequest) {
        try{
            if(CommonUtils.isNUllOrEmpty(updateBookRequest.getBookId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍ID不能为空");
            }
            BookDomain bookDomain = bookDao.queryBook(updateBookRequest.getBookId());
            if(bookDomain==null){
                return FreshServiceResult.createFailFreshServiceResult("书籍不存在");
            }
            bookDao.updateBook(updateBookRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("更新书籍成功");
        } catch (Exception e){
            String message = "更新书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult physicsDeleteBookByBookId(PhysicsDeleteBookByBookIdRequest physicsDeleteBookByBookIdRequest) {
        try{
            if(CommonUtils.isNUllOrEmpty(physicsDeleteBookByBookIdRequest.getBookId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍ID不能为空");
            }
            //校验软删除标识
            BookDomain bookDomain = bookDao.queryBook(physicsDeleteBookByBookIdRequest.getBookId());
            if(bookDomain==null){
                return FreshServiceResult.createFailFreshServiceResult("书籍不存在");
            }
            if(!bookDomain.isSoftDelete()){
                return FreshServiceResult.createFailFreshServiceResult("书籍软删除标识为不可删");
            }
            //校验 书籍下不能有章节
            List<BookChapterDomain> bookChapterDomainList = bookChapterDao.queryBookChapterListByBookId(physicsDeleteBookByBookIdRequest.getBookId());
            if(!CommonUtils.isNUllOrEmpty(bookChapterDomainList)){
                return FreshServiceResult.createFailFreshServiceResult("书籍下不能有章节");
            }

            bookDao.physicsDeleteBookByBookId(physicsDeleteBookByBookIdRequest.getBookId());
            return FreshServiceResult.createSuccessFreshServiceResult("删除书籍成功");
        } catch (Exception e){
            String message = "删除书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public ServiceResult<List<BookDTO>> queryBookList(QueryBookListRequest request) {
        try{
            List<BookDomain> bookDomainList = bookDao.queryBookList();
            List<BookDTO> bookDTOList = classCast(bookDomainList);
            
            return FreshServiceResult.createSuccessServiceResult("查询书籍列表成功",bookDTOList);
        } catch (Exception e){
            String message = "删除书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    private List<BookDTO> classCast(List<BookDomain> bookDomainList) {
        if(CommonUtils.isNUllOrEmpty(bookDomainList)){
            return null;
        }
        List<BookDTO> dtoList = new ArrayList<>();
        for (BookDomain bookDomain:bookDomainList){
            dtoList.add(classCast(bookDomain));
        }
        return dtoList;
    }

    private BookDTO classCast(BookDomain domain) {
        BookDTO dto = new BookDTO();
        dto.setBookId(domain.getBookId());
        dto.setAuthorId(domain.getAuthorId());
        dto.setBookName(domain.getBookName());
        dto.setCreateTime(domain.getCreateTime());
        dto.setSoftDelete(domain.isSoftDelete());
        return dto;
    }

    private BookDomain classCast2(AddBookRequest addBookRequest) {
        return classCast(addBookRequest);
    }

    private BookDomain classCast(BookDTO bookDTO) {
        BookDomain domain = new BookDomain();
        domain.setBookId(bookDTO.getBookId());
        domain.setAuthorId(bookDTO.getAuthorId());
        domain.setBookName(bookDTO.getBookName());
        domain.setCreateTime(bookDTO.getCreateTime());
        domain.setSoftDelete(bookDTO.isSoftDelete());
        return domain;
    }
}
