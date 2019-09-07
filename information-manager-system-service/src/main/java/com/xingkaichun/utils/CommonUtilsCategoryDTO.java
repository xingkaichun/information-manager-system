package com.xingkaichun.utils;

import com.xingkaichun.information.dto.category.CategoryDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonUtilsCategoryDTO {

    public static List<CategoryDTO> parentChildCategoryDTOList(List<CategoryDTO> categoryDTOList) {
        if(CommonUtils.isNUllOrEmpty(categoryDTOList)){
            return null;
        }
        List<CategoryDTO> rootCategoryDTOlist = new ArrayList<>();
        //根节点
        Iterator<CategoryDTO> iterator = categoryDTOList.iterator();
        while (iterator.hasNext()){
            CategoryDTO categoryDTO = iterator.next();
            if(CommonUtils.isNUllOrEmpty(categoryDTO.getParentCategoryId())){
                rootCategoryDTOlist.add(categoryDTO);
                iterator.remove();
            }
        }
        while(categoryDTOList.size()!=0){
            iterator = categoryDTOList.iterator();
            while (iterator.hasNext()){
                CategoryDTO categoryDTO = iterator.next();
                CategoryDTO parentCategoryDTO = findParentCategoryDTO(rootCategoryDTOlist,categoryDTO);
                if(parentCategoryDTO == null){
                    continue;
                }else{
                    parentCategoryDTO.getChildrenCategoryDTOList().add(categoryDTO);
                    iterator.remove();
                }
            }
        }
        return rootCategoryDTOlist;
    }

    private static CategoryDTO findParentCategoryDTO(List<CategoryDTO> rootCategoryDTOlist, CategoryDTO categoryDTO) {
        if(CommonUtils.isNUllOrEmpty(rootCategoryDTOlist)){
            throw new RuntimeException("根节点不能为空");
        }
        for(CategoryDTO rootCategoryDTO : rootCategoryDTOlist){
            if(rootCategoryDTO.getCategoryId().equals(categoryDTO.getParentCategoryId())){
                return rootCategoryDTO;
            }else{
                List<CategoryDTO> childList = rootCategoryDTO.getChildrenCategoryDTOList();
                if(!CommonUtils.isNUllOrEmpty(childList)){
                    CategoryDTO parentFind = findParentCategoryDTO(childList,categoryDTO);
                    if(parentFind != null){
                        return parentFind;
                    }
                }
            }
        }
        return null;
    }
}
