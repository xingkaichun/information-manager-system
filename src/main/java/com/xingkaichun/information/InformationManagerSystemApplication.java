package com.xingkaichun.information;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xingkaichun.information.dao")
public class InformationManagerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InformationManagerSystemApplication.class, args);
	}
}
