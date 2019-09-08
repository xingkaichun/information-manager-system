package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.bookSection.request.UpdateBookSectionRequest;
import com.xingkaichun.information.model.BookSectionDomian;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookSectionDao {
    int addBookSection(BookSectionDomian bookSectionDomian);
    int updateBookSection(UpdateBookSectionRequest request);
    int physicsDeleteBookSectionByBookSectionId(@Param("bookSectionId")String bookSectionId);

    List<BookSectionDomian> queryBookSectionListBybookChapterId(@Param("bookChapterId")String bookChapterId);
}
