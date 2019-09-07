package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.category.CategoryDTO;
import com.xingkaichun.information.dto.category.request.AddCategoryRequest;
import com.xingkaichun.information.dto.category.request.DeleteCategoryRequest;
import com.xingkaichun.information.dto.category.request.QueryCategoryRequest;
import com.xingkaichun.information.dto.category.request.UpdateCategoryRequest;
import com.xingkaichun.information.dto.category.response.QueryCategoryResponse;
import com.xingkaichun.information.service.ArticleService;
import com.xingkaichun.information.service.CategoryService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Api(value="文章类别controller",tags={"文章类别操作接口"})
@Controller
@RequestMapping(value = "/Category")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value="添加类别", notes="添加类别")
    @ResponseBody
    @PostMapping("/AddCategory")
    public FreshServiceResult addCategory(@RequestBody AddCategoryRequest addCategoryRequest, HttpServletRequest request){

        try{
            addCategoryRequest.setUserId(CommonUtilsSession.getUser(request).getUserId());

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

    @ApiOperation(value="有层次结构的全部类别", notes="有层次结构的全部类别")
    @ResponseBody
    @PostMapping("/QueryHierarchicalCategory")
    public ServiceResult<QueryCategoryResponse> queryHierarchicalCategory(@RequestBody QueryCategoryRequest queryCategoryRequest){
        try{
            QueryCategoryResponse queryCategoryResponse= categoryService.queryCategoryReturnHierarchicalStructure(queryCategoryRequest);
            return ServiceResult.createSuccessServiceResult("查询分类成功",queryCategoryResponse);
        } catch (Exception e){
            String message = "查询分类失败";
            LOGGER.error(message,e);
            return ServiceResult.createFailServiceResult(message);
        }
    }

    @ApiOperation(value="删除类别", notes="删除类别")
    @ResponseBody
    @PostMapping("/DeleteCategory")
    public FreshServiceResult deleteCategory(@RequestBody DeleteCategoryRequest deleteCategoryRequest){
        try{
            String categoryId = deleteCategoryRequest.getCategoryId();
            if(CommonUtils.isNUllOrEmpty(categoryId)){
                return FreshServiceResult.createFailFreshServiceResult("CategoryId不能为空");
            }
            //校验是否有子分类
            QueryCategoryRequest queryCategoryRequest = new QueryCategoryRequest();
            queryCategoryRequest.setParentCategoryId(categoryId);
            List<CategoryDTO> categoryDTOList = categoryService.queryCategoryReturnList(queryCategoryRequest);
            if(!CommonUtils.isNUllOrEmpty(categoryDTOList)){
                return FreshServiceResult.createFailFreshServiceResult("不能删除有子分类的分类");
            }
            //校验是否存在要删除分类的文章
            boolean hasArticle = articleService.hasArticleInCategoryId(categoryId);
            if(hasArticle){
                return FreshServiceResult.createFailFreshServiceResult("不能删除有文章的分类");
            }
            categoryService.deleteCategory(deleteCategoryRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("删除分类成功");
        } catch (Exception e){
            String message = "删除分类失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="更新类别", notes="更新类别")
    @ResponseBody
    @PostMapping("/UpdateCategory")
    public FreshServiceResult updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest){
        try{
            String categoryId = updateCategoryRequest.getCategoryId();
            if(CommonUtils.isNUllOrEmpty(categoryId)){
                return FreshServiceResult.createFailFreshServiceResult("分类ID不能为空");
            }
            if(CommonUtils.isNUllOrEmpty(updateCategoryRequest.getCategoryName())){
                return FreshServiceResult.createFailFreshServiceResult("分类名称不能为空");
            }
            categoryService.updateCategory(updateCategoryRequest);
            return FreshServiceResult.createSuccessFreshServiceResult("更新分类成功");
        } catch (Exception e){
            String message = "更新分类失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }
}
