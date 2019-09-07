package com.xingkaichun.information;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.xingkaichun.information.dao")
public class InformationManagerSystemAdminWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(InformationManagerSystemAdminWebApplication.class, args);
	}
}
