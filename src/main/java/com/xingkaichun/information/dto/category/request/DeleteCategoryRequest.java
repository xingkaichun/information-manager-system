package com.xingkaichun.information.dto.category.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteCategoryRequest {
    @JsonProperty("CategoryId")
    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
