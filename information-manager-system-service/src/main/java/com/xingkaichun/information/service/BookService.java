package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.AddBookRequest;
import com.xingkaichun.information.dto.book.request.PhysicsDeleteBookByBookIdRequest;
import com.xingkaichun.information.dto.book.request.QueryBookListRequest;
import com.xingkaichun.information.dto.book.request.UpdateBookRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BookService {

    ServiceResult<BookDTO> addBook(HttpServletRequest request, AddBookRequest addBookRequest);
    ServiceResult<BookDTO> updateBook(UpdateBookRequest request);
    ServiceResult<BookDTO> physicsDeleteBookByBookId(PhysicsDeleteBookByBookIdRequest request);

    ServiceResult<List<BookDTO>> queryBookList(QueryBookListRequest request);

    BookDTO queryBookByBookId(String bookId);

    boolean isHasOperateRight(HttpServletRequest httpServletRequest, String bookId);
}
