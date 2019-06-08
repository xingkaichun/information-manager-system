package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.FileDao;
import com.xingkaichun.information.model.FileDomain;
import com.xingkaichun.information.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    public int addFile(FileDomain fileDomain) {
        return fileDao.addFile(fileDomain);
    }
}
