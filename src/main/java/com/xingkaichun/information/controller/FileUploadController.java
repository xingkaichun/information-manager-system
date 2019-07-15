package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.file.FileDto;
import com.xingkaichun.information.dto.file.response.FileUploadResponse;
import com.xingkaichun.information.model.FileDomain;
import com.xingkaichun.information.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Api(value="文件controller",tags={"文件操作接口"})
@Controller
@RequestMapping(value = "/File")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private FileService fileService;

    @Value("${upload.fileDirectory}")
    private String uploadFileDirectory;

    @ApiOperation(value="文件上传", notes="文件上传")
    @PostMapping("/FileUpload")
    @ResponseBody
    public ServiceResult<FileUploadResponse> handleFileUpload(@RequestParam("file") MultipartFile file) {
        BufferedOutputStream bos = null;
        try{
            if(file.isEmpty()){
                return ServiceResult.createFailServiceResult("请选择文件上传");
            }
            String originalFilename = file.getOriginalFilename();
            String fileId = String.valueOf(UUID.randomUUID());
            String fileDescrible= "" ;
            String fileFormatSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileSaveName = fileId + fileFormatSuffix;
            String fileDirectory = uploadFileDirectory;
            String fileSavePath = fileDirectory + fileSaveName;
            bos = new BufferedOutputStream(new FileOutputStream(new File(fileSavePath)));
            bos.write(file.getBytes());
            bos.flush();

            fileService.addFile(new FileDomain(fileId,originalFilename,fileSaveName,fileDescrible,fileDirectory));
            FileDto fileDto = fileService.queryFileByFileIdReturnDto(fileId);
            return ServiceResult.createSuccessServiceResult(new FileUploadResponse(fileDto));
        }catch (Exception e){
            String message = "上传文件失败";
            LOGGER.error(message,e);
            return ServiceResult.createFailServiceResult(message);
        }finally {
            try{
                bos.close();
            } catch (Exception e1){
            }
        }
    }

    @ApiOperation(value="文件下载", notes="文件下载")
    @GetMapping("/FileDownload")
    public FreshServiceResult downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId")String fileId) throws UnsupportedEncodingException {

        // 实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            FileDomain fileDomain = fileService.queryFileByFileIdReturnDomain(fileId);
            String fileSavePath = fileDomain.getFileDirectory()+fileDomain.getFileSaveName();
            File file = new File(fileSavePath);

            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileDomain.getFileName(), "UTF-8"));

            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            String message = "下载文件失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
        }
        return FreshServiceResult.createSuccessFreshServiceResult("下载成功");
    }
}
