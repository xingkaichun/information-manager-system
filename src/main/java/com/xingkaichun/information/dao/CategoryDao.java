package com.xingkaichun.information.dao;

import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.model.CategoryDomain;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface CategoryDao {

    int addCategory(CategoryDomain categoryDomain);
    int deleteCategory(DeleteCategoryRequest deleteCategoryRequest);
    int updateCategory(CategoryDomain categoryDomain);

    CategoryDomain queryCategoryByCategoryId(String categoryId);
    List<CategoryDomain> queryCategoryByIds(Set<String> Ids);
    List<CategoryDomain> queryCategory(QueryCategoryRequest queryCategoryRequest);
}
