package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import com.xingkaichun.information.dto.bookChapter.request.*;

import java.util.List;

public interface BookChapterService {

    ServiceResult<BookChapterDTO> addBookChapter(AddBookChapterRequest request);
    ServiceResult<BookChapterDTO> updateBookChapter(UpdateBookChapterRequest request);
    ServiceResult<BookChapterDTO> physicsDeleteBookChapterByBookChapterId(PhysicsDeleteBookChapterByBookChapterIdRequest request);

    ServiceResult<List<BookChapterDTO>> queryBookChapterListByBookId(QueryBookChapterListByBookIdRequest request);

    BookChapterDTO queryBookChapterByBookChapterId(String bookChapterId);

    FreshServiceResult swapBookChapterOrder(SwapBookChapterOrderRequest request);
}
