package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.BookSectionDao;
import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.book.BookDTO;
import com.xingkaichun.information.dto.bookSection.BookSectionDTO;
import com.xingkaichun.information.dto.bookSection.request.AddBookSectionRequest;
import com.xingkaichun.information.dto.bookSection.request.PhysicsDeleteBookSectionByBookSectionIdRequest;
import com.xingkaichun.information.dto.bookSection.request.QueryBookSectionListBybookChapterIdRequest;
import com.xingkaichun.information.dto.bookSection.request.UpdateBookSectionRequest;
import com.xingkaichun.information.model.BookDomain;
import com.xingkaichun.information.model.BookSectionDomian;
import com.xingkaichun.information.service.BookSectionService;
import com.xingkaichun.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "bookSectionServiceImpl")
public class BookSectionServiceImpl implements BookSectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookSectionServiceImpl.class);

    @Autowired
    private BookSectionDao bokSectionDao;

    @Override
    public FreshServiceResult addBookSection(HttpServletRequest httpServletRequest, AddBookSectionRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍ID不能为空");
            } else {
                //TODO 校验书籍存在
            }
            if(CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节ID不能为空");
            } else {
                //TODO 校验书籍章节存在
            }
            if(CommonUtils.isNUllOrEmpty(request.getBookSectionName())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节名称不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(request.getBookSectionId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配书籍小节ID，请不要填写书籍小节ID");
            } else {
                request.setBookSectionId(String.valueOf(UUID.randomUUID()));
            }
            BookSectionDomian bookDomain = classCast2(request);
            bokSectionDao.addBookSection(bookDomain);
            return FreshServiceResult.createSuccessFreshServiceResult("新增书籍成功");
        } catch (Exception e){
            String message = "新增书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult updateBookSection(UpdateBookSectionRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookSectionId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节ID不能为空");
            }
            bokSectionDao.updateBookSection(request);
            return FreshServiceResult.createSuccessFreshServiceResult("更新书籍成功");
        } catch (Exception e){
            String message = "更新书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public FreshServiceResult physicsDeleteBookSectionByBookSectionId(PhysicsDeleteBookSectionByBookSectionIdRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookSectionId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍小节ID不能为空");
            }
            bokSectionDao.physicsDeleteBookSectionByBookSectionId(request.getBookSectionId());
            return FreshServiceResult.createSuccessFreshServiceResult("删除书籍小节成功");
        } catch (Exception e){
            String message = "删除书籍小节失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @Override
    public ServiceResult<List<BookSectionDTO>> queryBookSectionListBybookChapterId(QueryBookSectionListBybookChapterIdRequest request) {
        try{
            if(CommonUtils.isNUllOrEmpty(request.getBookChapterId())){
                return FreshServiceResult.createFailFreshServiceResult("书籍章节ID不能为空");
            }

            List<BookSectionDomian> bookSectionDomianList = bokSectionDao.queryBookSectionListBybookChapterId(request.getBookChapterId());
            List<BookSectionDTO> bookDTOList = classCast(bookSectionDomianList);
            
            return FreshServiceResult.createSuccessServiceResult("查询书籍列表成功",bookDTOList);
        } catch (Exception e){
            String message = "删除书籍失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    private List<BookSectionDTO> classCast(List<BookSectionDomian> domainList) {
        if(CommonUtils.isNUllOrEmpty(domainList)){
            return null;
        }
        List<BookSectionDTO> dtoList = new ArrayList<>();
        for (BookSectionDomian domian:domainList){
            dtoList.add(classCast(domian));
        }
        return dtoList;
    }

    private BookSectionDTO classCast(BookSectionDomian domain) {
        BookSectionDTO dto = new BookSectionDTO();
        dto.setBookId(domain.getBookId());
        dto.setBookChapterId(domain.getBookChapterId());
        dto.setBookSectionId(domain.getBookSectionId());
        dto.setBookSectionName(domain.getBookSectionName());
        dto.setBookSectionContent(domain.getBookSectionContent());
        dto.setBookSectionOrder(domain.getBookSectionOrder());

        dto.setCreateTime(domain.getCreateTime());
        dto.setLastEditTime(domain.getLastEditTime());
        dto.setSoftDelete(domain.isSoftDelete());
        return dto;
    }

    private BookSectionDomian classCast2(AddBookSectionRequest addBookRequest) {
        return classCast(addBookRequest);
    }

    private BookSectionDomian classCast(BookSectionDTO dto) {
        BookSectionDomian domain = new BookSectionDomian();
        domain.setBookId(dto.getBookId());
        domain.setBookChapterId(dto.getBookChapterId());
        domain.setBookSectionId(dto.getBookSectionId());
        domain.setBookSectionName(dto.getBookSectionName());
        domain.setBookSectionContent(dto.getBookSectionContent());
        domain.setBookSectionOrder(dto.getBookSectionOrder());

        domain.setCreateTime(dto.getCreateTime());
        domain.setLastEditTime(dto.getLastEditTime());
        domain.setSoftDelete(dto.isSoftDelete());
        return domain;
    }

}
