package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForHomeShowList;
import lombok.Data;

@Data
public class QueryBbsArticleCommentByUserResponse {


    @JsonProperty("BbsArticleCommentDTOPageInformation")
    private PageInformation<BbsArticleCommentDTOForHomeShowList> bbsArticleCommentDTOPageInformation;

}
