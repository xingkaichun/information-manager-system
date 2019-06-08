package com.xingkaichun.information.dao;

import com.xingkaichun.information.model.FileDomain;

import java.util.List;

public interface FileDao {
    int addFile(FileDomain fileDomain);

    List<FileDomain> queryFile(FileDomain fileDomain);
}
