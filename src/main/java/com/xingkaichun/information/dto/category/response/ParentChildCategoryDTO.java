package com.xingkaichun.information.dto.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.CategoryDTO;
import com.xingkaichun.information.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParentChildCategoryDTO {

    @JsonProperty("CategoryDTO")
    private CategoryDTO categoryDTO;
    @JsonProperty("ChildCategoryDTOList")
    private List<ParentChildCategoryDTO> childCategoryDTOList;

    public ParentChildCategoryDTO(CategoryDTO categoryDTO, List<ParentChildCategoryDTO> childCategoryDTOList) {
        this.categoryDTO = categoryDTO;
        this.childCategoryDTOList = childCategoryDTOList;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public List<ParentChildCategoryDTO> getChildCategoryDTOList() {
        return childCategoryDTOList;
    }

    public void setChildCategoryDTOList(List<ParentChildCategoryDTO> childCategoryDTOList) {
        this.childCategoryDTOList = childCategoryDTOList;
    }

    public static List<ParentChildCategoryDTO> parentChildCategoryDTOList(List<CategoryDTO> categoryDTOList) {
        if(CommonUtils.isNUllOrEmpty(categoryDTOList)){
            return null;
        }
        List<ParentChildCategoryDTO> pccDTOlist = new ArrayList<>();
        //根节点
        Iterator<CategoryDTO> iterator = categoryDTOList.iterator();
        while (iterator.hasNext()){
            CategoryDTO categoryDTO =iterator.next();
            if(CommonUtils.isNUllOrEmpty(categoryDTO.getParentCategoryId())){
                ParentChildCategoryDTO pccDTO = new ParentChildCategoryDTO(categoryDTO,new ArrayList<>());
                pccDTOlist.add(pccDTO);
                iterator.remove();
            }
        }
        while(categoryDTOList.size()!=0){
            iterator = categoryDTOList.iterator();
            while (iterator.hasNext()){
                CategoryDTO categoryDTO =iterator.next();
                ParentChildCategoryDTO pccDTO = findParentChildCategoryDTO(pccDTOlist,categoryDTO);
                if(pccDTO == null){
                    continue;
                }else{
                    pccDTO.getChildCategoryDTOList().add(new ParentChildCategoryDTO(categoryDTO,new ArrayList<>()));
                    iterator.remove();
                }
            }
        }
        return pccDTOlist;
    }

    private static ParentChildCategoryDTO findParentChildCategoryDTO(List<ParentChildCategoryDTO> pccDTOlist, CategoryDTO categoryDTO) {
        if(CommonUtils.isNUllOrEmpty(pccDTOlist)){
            throw new RuntimeException("根节点不能为空");
        }
        for(ParentChildCategoryDTO parentChildCategoryDTO : pccDTOlist){
            if(parentChildCategoryDTO.getCategoryDTO().getCategoryId().equals(categoryDTO.getParentCategoryId())){
                return parentChildCategoryDTO;
            }else{
                List<ParentChildCategoryDTO> childList = parentChildCategoryDTO.getChildCategoryDTOList();
                if(!CommonUtils.isNUllOrEmpty(childList)){
                    ParentChildCategoryDTO parentFind = findParentChildCategoryDTO(childList,categoryDTO);
                    if(parentChildCategoryDTO!=null){
                        return parentFind;
                    }
                }
            }
        }
        return null;
    }
}
