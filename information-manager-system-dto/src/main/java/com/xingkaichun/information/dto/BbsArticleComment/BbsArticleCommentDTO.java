package com.xingkaichun.information.dto.BbsArticleComment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.user.UserInfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BbsArticleCommentDTO {
    @JsonProperty("BbsArticleId")
    private String bbsArticleId;
    @JsonProperty("BbsArticleCommentId")
    private String bbsArticleCommentId;
    @JsonProperty("ParentBbsArticleCommentId")
    private String parentBbsArticleCommentId;
    @JsonProperty("UserId")
    private String userId;
    @JsonProperty("ToUserId")
    private String toUserId;
    @JsonProperty("UserInfo")
    private UserInfo userInfo;
    @JsonProperty("Content")
    private String content;
    @JsonProperty("CreateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonProperty("IsSoftDelete")
    private boolean isSoftDelete;
    @JsonProperty("ChildrenBbsArticleCommentDTOList")
    private List<BbsArticleCommentDTO> childrenBbsArticleCommentDTOList;

    @JsonProperty("ForBbsArticleCommentId")
    private String forBbsArticleCommentId;

    public BbsArticleCommentDTO() {
        childrenBbsArticleCommentDTOList = new ArrayList<>();
    }
}
