package com.xingkaichun.information.dto.BbsArticle.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTOForBbsShowList;
import lombok.Data;

import java.util.List;

@Data
public class QueryTwoUserBbsArticleCommentResponse {
    @JsonProperty("BbsArticleCommentDTOList")
    private List<BbsArticleCommentDTOForBbsShowList> bbsArticleCommentDTOList;
}
