package com.xingkaichun.information.model;

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDescrible() {
        return fileDescrible;
    }

    public void setFileDescrible(String fileDescrible) {
        this.fileDescrible = fileDescrible;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }
}
