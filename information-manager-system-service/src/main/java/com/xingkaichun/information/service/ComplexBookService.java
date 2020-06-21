package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.GetSectionPageUrlByBookSectionIdRequest;
import com.xingkaichun.information.dto.book.request.QueryBookDetailsByBookIdRequest;

public interface ComplexBookService {

    BookDTO queryBookDetailsByBookIdRequest(QueryBookDetailsByBookIdRequest request);

    void createHtmlPage(String bookId) throws Exception;

    void deleteHtmlPage(String bookId) throws Exception;

    ServiceResult<String> getSectionPageUrlByBookSectionId(String bookSectionId);
}
