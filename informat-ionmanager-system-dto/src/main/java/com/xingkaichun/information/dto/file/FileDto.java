package com.xingkaichun.information.dto.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
}
