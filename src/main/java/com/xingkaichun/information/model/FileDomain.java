package com.xingkaichun.information.model;

public class FileDomain {

    private String fileId;
    private String fileName;
    private String fileDescrible;
    private String filePath;

    public FileDomain() {
    }

    public FileDomain(String fileId, String fileName, String fileDescrible, String filePath) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileDescrible = fileDescrible;
        this.filePath = filePath;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
