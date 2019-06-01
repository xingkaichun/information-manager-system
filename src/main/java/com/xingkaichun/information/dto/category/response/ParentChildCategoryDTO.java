package com.xingkaichun.information.dto.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.CategoryDTO;

import java.util.List;

public class ParentChildCategoryDTO {

    @JsonProperty("ParentCategoryDTO")
    private CategoryDTO parentCategoryDTO;
    @JsonProperty("ChildCategoryDTOList")
    private List<CategoryDTO> childCategoryDTOList;

    public ParentChildCategoryDTO(CategoryDTO parentCategoryDTO, List<CategoryDTO> childCategoryDTOList) {
        this.parentCategoryDTO = parentCategoryDTO;
        this.childCategoryDTOList = childCategoryDTOList;
    }

    public CategoryDTO getParentCategoryDTO() {
        return parentCategoryDTO;
    }

    public void setParentCategoryDTO(CategoryDTO parentCategoryDTO) {
        this.parentCategoryDTO = parentCategoryDTO;
    }

    public List<CategoryDTO> getChildCategoryDTOList() {
        return childCategoryDTOList;
    }

    public void setChildCategoryDTOList(List<CategoryDTO> childCategoryDTOList) {
        this.childCategoryDTOList = childCategoryDTOList;
    }
}
