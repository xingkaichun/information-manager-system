package com.xingkaichun.information.dto.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.file.FileDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class ArticleDTO {

    @JsonProperty("ArticleId")
    private String articleId;
    @JsonProperty("CategoryId")
    private String categoryId;
    @JsonProperty("UserId")
    private String userId;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Content")
    private String content;
    @JsonProperty("CreateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonProperty("LastEditTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTime;
    @JsonProperty("AttachedFiles")
    private String attachedFiles;
    @JsonProperty("AttachedFilesDetails")
    private List<FileDto> attachedFilesDetails;
    @JsonProperty("IsSoftDelete")
    private Boolean isSoftDelete;

}
