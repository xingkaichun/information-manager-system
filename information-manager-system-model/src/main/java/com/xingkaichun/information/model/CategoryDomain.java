package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryDomain {

    private String categoryId;
    private String categoryName;
    private String parentCategoryId;
    private Date lastEditTime;
    private String userId;
}
