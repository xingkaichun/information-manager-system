package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import com.xingkaichun.information.dto.bookSection.request.AddBookSectionRequest;
import com.xingkaichun.information.dto.bookSection.request.PhysicsDeleteBookSectionByBookSectionIdRequest;
import com.xingkaichun.information.dto.bookSection.request.QueryBookSectionListBybookChapterIdRequest;
import com.xingkaichun.information.dto.bookSection.request.UpdateBookSectionRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BookSectionService {

    ServiceResult<BookSectionDTO> addBookSection(HttpServletRequest httpServletRequest, AddBookSectionRequest request);
    FreshServiceResult updateBookSection(UpdateBookSectionRequest request);
    FreshServiceResult physicsDeleteBookSectionByBookSectionId(PhysicsDeleteBookSectionByBookSectionIdRequest request);

    ServiceResult<List<BookSectionDTO>> queryBookSectionListBybookChapterId(QueryBookSectionListBybookChapterIdRequest request);

    BookSectionDTO queryBookSectionDTOBySectionId(String bookSectionId);
}
