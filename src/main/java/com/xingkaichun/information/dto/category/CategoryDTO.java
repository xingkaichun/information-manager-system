package com.xingkaichun.information.dto.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CategoryDTO> getChildrenCategoryDTOList() {
        return childrenCategoryDTOList;
    }

    public void setChildrenCategoryDTOList(List<CategoryDTO> childrenCategoryDTOList) {
        this.childrenCategoryDTOList = childrenCategoryDTOList;
    }
}
