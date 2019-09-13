package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.book.request.UpdateBookRequest;
import com.xingkaichun.information.model.BookDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookDao {

    int addBook(BookDomain bookDomain);
    int updateBook(UpdateBookRequest updateBookRequest);
    int physicsDeleteBookByBookId(@Param("bookId")String bookId);

    List<BookDomain> queryBookList();
    BookDomain queryBookByBookId(@Param("bookId")String bookId);
}
