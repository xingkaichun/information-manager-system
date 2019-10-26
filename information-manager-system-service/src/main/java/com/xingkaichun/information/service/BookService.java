package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.*;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BookService {

    ServiceResult<BookDTO> addBook(HttpServletRequest request, AddBookRequest addBookRequest);
    FreshServiceResult updateBook(UpdateBookRequest request);
    FreshServiceResult physicsDeleteBookByBookId(PhysicsDeleteBookByBookIdRequest request);

    ServiceResult<List<BookDTO>> queryBookList(QueryBookListRequest request);

    BookDTO queryBook(QueryBookDetailsByBookIdRequest request);

    boolean isHasOperateRight(HttpServletRequest httpServletRequest, String bookId);

    List<BookDTO> queryBookListByBookIds(List<String> bookIds);
}
