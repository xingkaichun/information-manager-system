package com.xingkaichun.information.service.user.impl;

import com.xingkaichun.information.dao.CategoryDao;
import com.xingkaichun.information.dto.CategoryDTO;
import com.xingkaichun.information.dto.category.request.AddCategoryRequest;
import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.utils.CommonUtilsCategoryDTO;
import com.xingkaichun.information.dto.category.response.QueryCategoryResponse;
import com.xingkaichun.information.model.CategoryDomain;
import com.xingkaichun.information.service.user.CategoryService;
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
        CategoryDomain CategoryDomain = classCastCategoryDTO2CategoryDomain(addCategoryRequest);
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
        return new QueryCategoryResponse(CommonUtilsCategoryDTO.parentChildCategoryDTOList(classCastCategoryDomain2CategoryDTO(categoryDomainList)));
    }

    @Override
    public List<CategoryDTO> queryCategoryReturnList(QueryCategoryRequest queryCategoryRequest) {
        List<CategoryDomain> categoryDomainList = categoryDao.queryCategory(queryCategoryRequest);
        return classCastCategoryDomain2CategoryDTO(categoryDomainList);
    }

    @Override
    public int deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {
        return categoryDao.deleteCategory(deleteCategoryRequest);
    }

    private List<CategoryDTO> classCastCategoryDomain2CategoryDTO(List<CategoryDomain> categoryDomainList) {
        if(CommonUtils.isNUllOrEmpty(categoryDomainList)){
            return null;
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDomainList.forEach(categoryDomain -> categoryDTOList.add(classCastCategoryDomain2CategoryDTO(categoryDomain)));
        return categoryDTOList;
    }

    private CategoryDTO classCastCategoryDomain2CategoryDTO(CategoryDomain categoryDomain) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryDomain.getCategoryId());
        categoryDTO.setCategoryName(categoryDomain.getCategoryName());
        categoryDTO.setUserId(categoryDomain.getUserId());
        categoryDTO.setParentCategoryId(categoryDomain.getParentCategoryId());
        categoryDTO.setLastEditTime(categoryDomain.getLastEditTime());
        return categoryDTO;
    }


    private static CategoryDomain classCastCategoryDTO2CategoryDomain(CategoryDTO categoryDTO) {
        CategoryDomain categoryDomain = new CategoryDomain();
        categoryDomain.setCategoryId(categoryDTO.getCategoryId());
        categoryDomain.setCategoryName(categoryDTO.getCategoryName());
        categoryDomain.setUserId(categoryDTO.getUserId());
        categoryDomain.setParentCategoryId(categoryDTO.getParentCategoryId());
        categoryDomain.setLastEditTime(categoryDTO.getLastEditTime());
        return categoryDomain;
    }
}
