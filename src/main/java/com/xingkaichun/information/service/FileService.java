package com.xingkaichun.information.service;

import com.xingkaichun.information.model.FileDomain;

import java.util.List;

public interface FileService {

    int addFile(FileDomain fileDomain);
    List<FileDomain> queryFile(FileDomain fileDomain);
}
