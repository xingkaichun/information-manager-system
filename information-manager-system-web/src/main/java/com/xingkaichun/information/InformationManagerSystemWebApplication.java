package com.xingkaichun.information;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.xingkaichun.information.dao")
public class InformationManagerSystemWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(InformationManagerSystemWebApplication.class, args);
	}
}
