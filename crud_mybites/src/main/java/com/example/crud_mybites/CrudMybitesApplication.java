package com.example.crud_mybites;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.crud_mybites.persistence")
@SpringBootApplication
public class CrudMybitesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudMybitesApplication.class, args);
	}

}
