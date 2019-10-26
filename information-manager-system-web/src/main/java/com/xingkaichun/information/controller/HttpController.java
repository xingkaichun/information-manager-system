package com.xingkaichun.information.controller;

import com.alibaba.fastjson.JSONObject;
import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Api(value="HttpController",tags={"请求转发"})
@Controller
@RequestMapping(value = "/Http")
public class HttpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpController.class);

    @ResponseBody
    @PostMapping("/POST")
    public ServiceResult<JSONObject> Get2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody JSONObject requestBody) {
        try {
            String body1 = requestBody.toString();

            String forwardRequestUrl = httpServletRequest.getHeader("forwardRequestUrl");
            String forwardRequestMethod = httpServletRequest.getHeader("forwardRequestMethod");

            OutputStreamWriter out = null;
            InputStream is = null;
            try {
                URL url = new URL(forwardRequestUrl);// 创建连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod(forwardRequestMethod); // 设置请求方式
                connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
                connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
                connection.connect();
                out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
                out.append(body1);
                out.flush();
                out.close();

                // 读取响应
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    is = connection.getInputStream();
                } else {
                    is = connection.getErrorStream();
                }
                int length = (int) connection.getContentLength();// 获取长度
                if (length != -1) {
                    byte[] data = new byte[length];
                    byte[] temp = new byte[512];
                    int readLen = 0;
                    int destPos = 0;
                    while ((readLen = is.read(temp)) > 0) {
                        System.arraycopy(temp, 0, data, destPos, readLen);
                        destPos += readLen;
                    }
                    String contentType = connection.getHeaderField("content-type");
                    String result = new String(data, "UTF-8"); // utf-8编码
                    JSONObject resultJsonObject = JSONObject.parseObject(result);
                    return ServiceResult.createSuccessServiceResult("成功",resultJsonObject);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            return FreshServiceResult.createFailFreshServiceResult("失败");
        }
        return FreshServiceResult.createFailFreshServiceResult("失败");
    }
}
