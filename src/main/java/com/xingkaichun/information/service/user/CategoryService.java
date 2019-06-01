package com.xingkaichun.information.service.user;

import com.xingkaichun.information.dto.CategoryDTO;
import com.xingkaichun.information.dto.category.request.AddCategoryRequest;
import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.dto.category.response.QueryCategoryResponse;

import java.util.List;

public interface CategoryService {

    int addCategory(AddCategoryRequest addCategoryRequest);

    QueryCategoryResponse queryCategoryReturnHierarchicalStructure(QueryCategoryRequest queryCategoryRequest);

    List<CategoryDTO> queryCategoryReturnList(QueryCategoryRequest queryCategoryRequest);

    int deleteCategory(DeleteCategoryRequest deleteCategoryRequest);
}
