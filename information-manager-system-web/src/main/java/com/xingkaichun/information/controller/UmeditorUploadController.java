package com.xingkaichun.information.controller;

import com.alibaba.fastjson.JSONObject;
import com.xingkaichun.information.dto.file.FileDto;
import com.xingkaichun.information.model.FileDomain;
import com.xingkaichun.information.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Api(value="文件controller",tags={"文件操作接口"})
@Controller
public class UmeditorUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmeditorUploadController.class);

    @Autowired
    private FileService fileService;

    @Value("${upload.fileDirectory}")
    private String uploadFileDirectory;



    @RequestMapping("/umeditor/upload")
    @ResponseBody
    public void umeditorUpload(@RequestParam("upfile") MultipartFile file,HttpServletRequest request,HttpServletResponse response)
            throws IllegalStateException, IOException{

        BufferedOutputStream bos = null;
        try{
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

            JSONObject json = new JSONObject();
            json.put("state","SUCCESS");
            json.put("original",file.getOriginalFilename());
            json.put("size",file.getSize());
            json.put("url",fileDto.getDownPath());
            json.put("title", fileDto.getFileName());
            String type="."+file.getOriginalFilename().split("\\.")[1];
            json.put("type",type);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer=response.getWriter();
            writer.write(json.toString());
            writer.close();
        }catch (Exception e){
            String message = "上传文件失败";
            LOGGER.error(message,e);
        }finally {
            try{
                bos.close();
            } catch (Exception e1){
            }
        }
    }

    @ApiOperation(value="文件下载", notes="文件下载")
    @RequestMapping(value = "/FileDownload", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId")String fileId) throws UnsupportedEncodingException {
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
    }
}
