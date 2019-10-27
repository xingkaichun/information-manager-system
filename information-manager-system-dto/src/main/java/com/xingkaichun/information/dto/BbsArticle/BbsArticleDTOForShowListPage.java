package com.xingkaichun.information.dto.BbsArticle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BbsArticleDTOForShowListPage extends BbsArticleDTO {

    @JsonProperty("UserName")
    private String userName;

    @JsonProperty("NumberOfComment")
    private int numberOfComment;

}
