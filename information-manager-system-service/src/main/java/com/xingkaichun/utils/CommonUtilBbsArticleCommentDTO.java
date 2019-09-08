package com.xingkaichun.utils;

import com.xingkaichun.information.dto.BbsArticleComment.BbsArticleCommentDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonUtilBbsArticleCommentDTO {

    public static List<BbsArticleCommentDTO> parentBbsArticleCommentDTOList(List<BbsArticleCommentDTO> categoryDTOList) {
        if(CommonUtils.isNUllOrEmpty(categoryDTOList)){
            return null;
        }
        List<BbsArticleCommentDTO> rootCategoryDTOlist = new ArrayList<>();
        //根节点
        Iterator<BbsArticleCommentDTO> iterator = categoryDTOList.iterator();
        while (iterator.hasNext()){
            BbsArticleCommentDTO categoryDTO = iterator.next();
            if(CommonUtils.isNUllOrEmpty(categoryDTO.getParentBbsArticleCommentId())){
                rootCategoryDTOlist.add(categoryDTO);
                iterator.remove();
            }
        }
        while(categoryDTOList.size()!=0){
            iterator = categoryDTOList.iterator();
            while (iterator.hasNext()){
                BbsArticleCommentDTO categoryDTO = iterator.next();
                BbsArticleCommentDTO parentCategoryDTO = findParentCategoryDTO(rootCategoryDTOlist,categoryDTO);
                if(parentCategoryDTO == null){
                    continue;
                }else{
                    parentCategoryDTO.getChildrenBbsArticleCommentDTOList().add(categoryDTO);
                    iterator.remove();
                }
            }
        }
        return rootCategoryDTOlist;
    }

    private static BbsArticleCommentDTO findParentCategoryDTO(List<BbsArticleCommentDTO> rootCategoryDTOlist, BbsArticleCommentDTO categoryDTO) {
        if(CommonUtils.isNUllOrEmpty(rootCategoryDTOlist)){
            throw new RuntimeException("根节点不能为空");
        }
        for(BbsArticleCommentDTO rootCategoryDTO : rootCategoryDTOlist){
            if(rootCategoryDTO.getBbsArticleCommentId().equals(categoryDTO.getParentBbsArticleCommentId())){
                return rootCategoryDTO;
            }else{
                List<BbsArticleCommentDTO> childList = rootCategoryDTO.getChildrenBbsArticleCommentDTOList();
                if(!CommonUtils.isNUllOrEmpty(childList)){
                    BbsArticleCommentDTO parentFind = findParentCategoryDTO(childList,categoryDTO);
                    if(parentFind != null){
                        return parentFind;
                    }
                }
            }
        }
        return null;
    }
}
