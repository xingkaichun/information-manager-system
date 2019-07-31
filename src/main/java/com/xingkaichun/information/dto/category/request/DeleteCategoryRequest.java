package com.xingkaichun.information.dto.category.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteCategoryRequest {
    @JsonProperty("CategoryId")
    private String categoryId;
}
