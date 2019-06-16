package com.xingkaichun.information.dto.file;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDto {

    @JsonProperty("FileId")
    private String fileId;
    @JsonProperty("FileName")
    private String fileName;
    @JsonProperty("FileDescrible")
    private String fileDescrible;
    @JsonProperty("DownPath")
    private String downPath;
    @JsonProperty("FilePath")
    private String filePath;

    public FileDto() {
    }

    public FileDto(String fileId, String fileName, String fileDescrible, String downPath, String filePath) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileDescrible = fileDescrible;
        this.downPath = downPath;
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

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
