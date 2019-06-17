package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.category.CategoryDTO;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.category.request.AddCategoryRequest;
import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.dto.category.request.UpdateCategoryRequest;
import com.xingkaichun.information.dto.category.response.QueryCategoryResponse;
import com.xingkaichun.information.service.CategoryService;
import com.xingkaichun.information.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/Category")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @ResponseBody
    @PostMapping("/AddCategory")
    public FreshServiceResult addCategory(@RequestBody AddCategoryRequest addCategoryRequest){

        try{
            if(CommonUtils.isNUllOrEmpty(addCategoryRequest.getCategoryName())){
                return FreshServiceResult.createFailFreshServiceResult("CategoryName不能为空");
            }

            //如果插入子类别，校验父类别是否存在
            String parentCategoryId = addCategoryRequest.getParentCategoryId();
            if(!CommonUtils.isNUllOrEmpty(parentCategoryId)){
                CategoryDTO categoryDTO = categoryService.queryCategoryByCategoryId(parentCategoryId);
                if(CommonUtils.isNUll(categoryDTO)){
                    return FreshServiceResult.createFailFreshServiceResult("添加二级分类时,父分类ParentCategoryId必须存在");
                }
            }

            //自动生成id
            if(!CommonUtils.isNUllOrEmpty(addCategoryRequest.getCategoryId())){
                return FreshServiceResult.createFailFreshServiceResult("添加分类时,分类Id:CategoryId不能赋值,它的值系统自动分配");
            }else{
                addCategoryRequest.setCategoryId(UUID.randomUUID().toString());
            }

            categoryService.addCategory(addCategoryRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("新增分类成功");
        } catch (Exception e){
            String message = "新增分类失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ResponseBody
    @PostMapping("/QueryCategory")
    public ServiceResult<QueryCategoryResponse> queryCategory(@RequestBody QueryCategoryRequest queryCategoryRequest){
        try{
            QueryCategoryResponse queryCategoryResponse= categoryService.queryCategoryReturnHierarchicalStructure(queryCategoryRequest);
            return ServiceResult.createSuccessServiceResult(queryCategoryResponse);
        } catch (Exception e){
            String message = "查询分类失败";
            LOGGER.error(message,e);
            return ServiceResult.createFailServiceResult(message);
        }
    }

    @ResponseBody
    @PostMapping("/DeleteCategory")
    public FreshServiceResult deleteCategory(@RequestBody DeleteCategoryRequest deleteCategoryRequest){
        String categoryId = deleteCategoryRequest.getCategoryId();
        if(CommonUtils.isNUllOrEmpty(categoryId)){
            return FreshServiceResult.createFailFreshServiceResult("CategoryId不能为空");
        }
        try{
            //校验是否有子分类
            QueryCategoryRequest queryCategoryRequest = new QueryCategoryRequest();
            queryCategoryRequest.setParentCategoryId(categoryId);
            List<CategoryDTO> categoryDTOList = categoryService.queryCategoryReturnList(queryCategoryRequest);
            if(!CommonUtils.isNUllOrEmpty(categoryDTOList)){
                return FreshServiceResult.createFailFreshServiceResult("不能删除有子分类的分类");
            }
            //TODO 校验分类下是否有文章

            categoryService.deleteCategory(deleteCategoryRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("删除分类成功");
        } catch (Exception e){
            String message = "删除分类失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ResponseBody
    @PostMapping("/UpdateCategory")
    public FreshServiceResult updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest){
        String categoryId = updateCategoryRequest.getCategoryId();
        if(CommonUtils.isNUllOrEmpty(categoryId)){
            return FreshServiceResult.createFailFreshServiceResult("CategoryId不能为空");
        }
        try{
            categoryService.updateCategory(updateCategoryRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("更新分类成功");
        } catch (Exception e){
            String message = "更新分类失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }
}
