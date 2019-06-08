package com.xingkaichun.information.dto.file.response;

public class FileUploadResponse {
    //文件地址
    private String filePath;

    public FileUploadResponse() {
    }

    public FileUploadResponse(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
