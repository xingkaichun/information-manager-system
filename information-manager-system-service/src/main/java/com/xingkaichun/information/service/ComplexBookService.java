package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.book.request.QueryBookDetailsByBookIdRequest;

public interface ComplexBookService {

    BookDTO queryBookDetailsByBookIdRequest(QueryBookDetailsByBookIdRequest request);
}