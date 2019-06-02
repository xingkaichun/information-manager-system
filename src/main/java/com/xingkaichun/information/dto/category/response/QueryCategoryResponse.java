package com.xingkaichun.information.dto.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.CategoryDTO;

import java.util.List;

public class QueryCategoryResponse {

    @JsonProperty("ParentChildCategoryDTOList")
    private List<ParentChildCategoryDTO> parentChildCategoryDTOList;

    public QueryCategoryResponse(List<ParentChildCategoryDTO> parentChildCategoryDTOList) {
        this.parentChildCategoryDTOList = parentChildCategoryDTOList;
    }

    public List<ParentChildCategoryDTO> getParentChildCategoryDTOList() {
        return parentChildCategoryDTOList;
    }

    public void setParentChildCategoryDTOList(List<ParentChildCategoryDTO> parentChildCategoryDTOList) {
        this.parentChildCategoryDTOList = parentChildCategoryDTOList;
    }
}
