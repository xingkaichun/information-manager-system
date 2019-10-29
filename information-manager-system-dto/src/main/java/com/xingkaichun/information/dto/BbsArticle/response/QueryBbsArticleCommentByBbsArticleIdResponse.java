package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageInformation;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForBbsShowList;
import lombok.Data;

@Data
public class QueryBbsArticleCommentByBbsArticleIdResponse {
    @JsonProperty("BbsArticleCommentDTOInformation")
    private PageInformation<BbsArticleCommentDTOForBbsShowList> bbsArticleCommentDTOInformation;
}
