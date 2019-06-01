package com.xingkaichun.information.service.user.impl;

import com.xingkaichun.information.dao.CategoryDao;
import com.xingkaichun.information.dto.CategoryDTO;
import com.xingkaichun.information.dto.category.request.AddCategoryRequest;
import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.dto.category.response.ParentChildCategoryDTO;
import com.xingkaichun.information.dto.category.response.QueryCategoryResponse;
import com.xingkaichun.information.model.CategoryDomain;
import com.xingkaichun.information.service.user.CategoryService;
import com.xingkaichun.information.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Map<String,ParentChildCategoryDTO> parentCategoryId_ParentChildCategoryDTO_Map = new HashMap<>();
        categoryDomainList.forEach(categoryDomain -> {
            CategoryDTO categoryDTO = classCastCategoryDomain2CategoryDTO(categoryDomain);
            String categoryId = categoryDTO.getCategoryId();
            String parentCategoryId = categoryDTO.getParentCategoryId();
            if(CommonUtils.isNUllOrEmpty(parentCategoryId)){
                //一级分类
                if(!parentCategoryId_ParentChildCategoryDTO_Map.containsKey(categoryId)){
                    parentCategoryId_ParentChildCategoryDTO_Map.put(categoryId,new ParentChildCategoryDTO(categoryDTO,new ArrayList<>()));
                }
            }
        });
        categoryDomainList.forEach(categoryDomain -> {
            CategoryDTO categoryDTO = classCastCategoryDomain2CategoryDTO(categoryDomain);
            String categoryId = categoryDTO.getCategoryId();
            String parentCategoryId = categoryDTO.getParentCategoryId();
            if(!CommonUtils.isNUllOrEmpty(parentCategoryId)){
                //二级分类
                ParentChildCategoryDTO parentChildCategoryDTO = parentCategoryId_ParentChildCategoryDTO_Map.get(parentCategoryId);
                if(parentChildCategoryDTO != null){
                    parentChildCategoryDTO.getChildCategoryDTOList().add(categoryDTO);
                }else{
                    List<CategoryDTO> childCategoryDTOList = new ArrayList<>();
                    childCategoryDTOList.add(categoryDTO);

                    QueryCategoryRequest qRequest = new QueryCategoryRequest();
                    qRequest.setCategoryId(parentCategoryId);
                    List<CategoryDomain> cdList = categoryDao.queryCategory(qRequest);
                    CategoryDTO parentCategoryDTO = classCastCategoryDomain2CategoryDTO(cdList.get(0));

                    parentCategoryId_ParentChildCategoryDTO_Map.put(categoryId,new ParentChildCategoryDTO(parentCategoryDTO,childCategoryDTOList));
                }
            }
        });

        List<ParentChildCategoryDTO> parentChildCategoryDTOList = new ArrayList<>(parentCategoryId_ParentChildCategoryDTO_Map.values());
        return new QueryCategoryResponse(parentChildCategoryDTOList);
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
