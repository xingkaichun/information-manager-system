package com.xingkaichun.information.dto.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDTO {

    @JsonProperty("CategoryId")
    private String categoryId;
    @JsonProperty("CategoryName")
    private String categoryName;
    @JsonProperty("ParentCategoryId")
    private String parentCategoryId;
    @JsonProperty("LastEditTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTime;
    @JsonProperty("UserId")
    private String userId;

    @JsonProperty("Children")
    private List<CategoryDTO> childrenCategoryDTOList;

    public CategoryDTO() {
        childrenCategoryDTOList = new ArrayList<>();
    }
}
