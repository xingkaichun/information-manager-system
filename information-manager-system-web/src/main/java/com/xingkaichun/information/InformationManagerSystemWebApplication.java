package com.xingkaichun.information;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.xingkaichun.information.dao")
public class InformationManagerSystemWebApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(InformationManagerSystemWebApplication.class, args);


		//TODO 优雅方式
		String fileSystemUploadFileUrl = context.getEnvironment().getProperty("FileSystem.UploadFileUrl");
		System.setProperty("FileSystem.UploadFileUrl",fileSystemUploadFileUrl);
	}


}
