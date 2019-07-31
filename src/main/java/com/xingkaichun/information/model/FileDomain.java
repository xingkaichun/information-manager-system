package com.xingkaichun.information.model;

import lombok.Data;

@Data
public class FileDomain {

    private String fileId;
    private String fileName;
    private String fileSaveName;
    private String fileDescrible;
    private String fileDirectory;

    public FileDomain() {
    }

    public FileDomain(String fileId, String fileName, String fileSaveName, String fileDescrible, String fileDirectory) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSaveName = fileSaveName;
        this.fileDescrible = fileDescrible;
        this.fileDirectory = fileDirectory;
    }
}
