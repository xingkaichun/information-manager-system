package com.xingkaichun.information.dto.BbsArticle.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.common.dto.base.page.PageCondition;
import com.xingkaichun.information.dto.BbsArticle.BbsArticleDTO;
import lombok.Data;

@Data
public class QueryBbsArticleRequest extends BbsArticleDTO {
    @JsonProperty("PageCondition")
    PageCondition pageCondition;

    @JsonProperty("OrderByField")
    private String orderByField;

    public String getOrderByField() {
        if(!"CreateTime".equals(orderByField)&&!"LastEditTime".equals(orderByField)){
            throw new IllegalArgumentException("非法参数"+orderByField);
        }
        return orderByField;
    }
}
