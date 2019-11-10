package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.bookChapter.BookChapterDTO;
import com.xingkaichun.information.dto.bookChapter.request.*;

import java.util.List;

public interface BookChapterService {

    ServiceResult<BookChapterDTO> addBookChapter(AddBookChapterRequest request);
    FreshServiceResult updateBookChapter(UpdateBookChapterRequest request);
    FreshServiceResult physicsDeleteBookChapterByBookChapterId(PhysicsDeleteBookChapterByBookChapterIdRequest request);

    ServiceResult<List<BookChapterDTO>> queryBookChapterListByBookId(QueryBookChapterListByBookIdRequest request);

    BookChapterDTO queryBookChapterByBookChapterId(String bookChapterId);

    FreshServiceResult swapBookChapterOrder(SwapBookChapterOrderRequest request);
}
