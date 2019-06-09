package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.file.FileDto;
import com.xingkaichun.information.model.FileDomain;

import java.util.List;

public interface FileService {

    int addFile(FileDomain fileDomain);
    List<FileDto> queryFile(FileDomain fileDomain);
    FileDto queryFileByFileIdReturnDto(String fileId);
    FileDomain queryFileByFileIdReturnDomain(String fileId);
}
