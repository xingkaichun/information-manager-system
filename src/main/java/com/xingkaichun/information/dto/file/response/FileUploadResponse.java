package com.xingkaichun.information.dto.file.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.file.FileDto;

public class FileUploadResponse {

    @JsonProperty("FileDto")
    private FileDto fileDto;

    public FileUploadResponse() {
    }

    public FileUploadResponse(FileDto fileDto) {
        this.fileDto = fileDto;
    }

    public FileDto getFileDto() {
        return fileDto;
    }

    public void setFileDto(FileDto fileDto) {
        this.fileDto = fileDto;
    }
}
