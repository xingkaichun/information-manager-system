package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.model.CategoryDomain;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CategoryDao {

    int addCategory(CategoryDomain categoryDomain);

    List<CategoryDomain> queryCategory(QueryCategoryRequest queryCategoryRequest);

    int deleteCategory(DeleteCategoryRequest deleteCategoryRequest);
}
