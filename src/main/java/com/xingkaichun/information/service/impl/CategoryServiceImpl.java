package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.CategoryDao;
import com.xingkaichun.information.dto.category.CategoryDTO;
import com.xingkaichun.information.dto.category.request.AddCategoryRequest;
import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.dto.category.request.UpdateCategoryRequest;
import com.xingkaichun.information.utils.CommonUtilsCategoryDTO;
import com.xingkaichun.information.dto.category.response.QueryCategoryResponse;
import com.xingkaichun.information.model.CategoryDomain;
import com.xingkaichun.information.service.CategoryService;
import com.xingkaichun.information.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public int addCategory(AddCategoryRequest addCategoryRequest) {
        CategoryDomain CategoryDomain = classCast(addCategoryRequest);
        return categoryDao.addCategory(CategoryDomain);
    }

    @Override
    public QueryCategoryResponse queryCategoryReturnHierarchicalStructure(QueryCategoryRequest queryCategoryRequest) {
        List<CategoryDomain> categoryDomainList = categoryDao.queryCategory(queryCategoryRequest);

        if(CommonUtils.isNUllOrEmpty(categoryDomainList)){
            return null;
        }
        while(true){
            Stream<String > categoryIdStream = categoryDomainList.stream()
                    .map(categoryDomain -> categoryDomain.getCategoryId());
            Set<String> categoryIdSet = CommonUtils.Stream2Set(categoryIdStream);

            Stream<String > parentCategoryIdStream = categoryDomainList.stream().filter(categoryDomain -> !CommonUtils.isNUllOrEmpty(categoryDomain.getParentCategoryId()))
                    .map(categoryDomain -> categoryDomain.getParentCategoryId());
            Set<String> parentCategoryIdSet = CommonUtils.Stream2Set(parentCategoryIdStream);

            parentCategoryIdSet.removeAll(categoryIdSet);
            if(parentCategoryIdSet.size()==0){
                break;
            }
            List<CategoryDomain> categoryDomainListFindByIds = categoryDao.queryCategoryByIds(parentCategoryIdSet);
            categoryDomainList.addAll(categoryDomainListFindByIds);
        }
        return new QueryCategoryResponse(CommonUtilsCategoryDTO.parentChildCategoryDTOList(classCast(categoryDomainList)));
    }

    @Override
    public List<CategoryDTO> queryCategoryReturnList(QueryCategoryRequest queryCategoryRequest) {
        List<CategoryDomain> categoryDomainList = categoryDao.queryCategory(queryCategoryRequest);
        return classCast(categoryDomainList);
    }

    @Override
    public int deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {
        return categoryDao.deleteCategory(deleteCategoryRequest);
    }

    @Override
    public int updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        CategoryDomain CategoryDomain = classCast(updateCategoryRequest);
        return categoryDao.updateCategory(CategoryDomain);
    }

    private List<CategoryDTO> classCast(List<CategoryDomain> categoryDomainList) {
        if(CommonUtils.isNUllOrEmpty(categoryDomainList)){
            return null;
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDomainList.forEach(categoryDomain -> categoryDTOList.add(classCast(categoryDomain)));
        return categoryDTOList;
    }

    private CategoryDTO classCast(CategoryDomain categoryDomain) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryDomain.getCategoryId());
        categoryDTO.setCategoryName(categoryDomain.getCategoryName());
        categoryDTO.setUserId(categoryDomain.getUserId());
        categoryDTO.setParentCategoryId(categoryDomain.getParentCategoryId());
        categoryDTO.setLastEditTime(categoryDomain.getLastEditTime());
        return categoryDTO;
    }


    private static CategoryDomain classCast(CategoryDTO categoryDTO) {
        CategoryDomain categoryDomain = new CategoryDomain();
        categoryDomain.setCategoryId(categoryDTO.getCategoryId());
        categoryDomain.setCategoryName(categoryDTO.getCategoryName());
        categoryDomain.setUserId(categoryDTO.getUserId());
        categoryDomain.setParentCategoryId(categoryDTO.getParentCategoryId());
        categoryDomain.setLastEditTime(categoryDTO.getLastEditTime());
        return categoryDomain;
    }
}
