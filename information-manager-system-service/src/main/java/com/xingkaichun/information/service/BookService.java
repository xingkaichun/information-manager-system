package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.AddBookRequest;
import com.xingkaichun.information.dto.book.request.PhysicsDeleteBookByBookIdRequest;
import com.xingkaichun.information.dto.book.request.UpdateBookRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BookService {

    ServiceResult<BookDTO> addBook(HttpServletRequest request, AddBookRequest addBookRequest);
    FreshServiceResult updateBook(UpdateBookRequest request);
    FreshServiceResult physicsDeleteBookByBookId(PhysicsDeleteBookByBookIdRequest request);

    ServiceResult<List<BookDTO>> queryBookList();
}
