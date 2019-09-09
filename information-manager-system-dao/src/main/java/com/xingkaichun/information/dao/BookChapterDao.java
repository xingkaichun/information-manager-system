package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.bookChapter.request.UpdateBookChapterRequest;
import com.xingkaichun.information.model.BookChapterDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookChapterDao {
    int addBookChapter(BookChapterDomain bookChapterDomain);
    int updateBookChapter(UpdateBookChapterRequest bookChapterDomain);
    int physicsDeleteBookChapterByBookChapterId(@Param("bookChapterId")String bookChapterId);

    List<BookChapterDomain> queryBookChapterListByBookId(@Param("bookId")String bookId);
    BookChapterDomain queryBookChapterByBookChapterId(@Param("bookChapterId")String bookChapterId);
}
