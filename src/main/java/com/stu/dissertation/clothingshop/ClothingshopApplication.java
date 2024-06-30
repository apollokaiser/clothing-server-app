package com.stu.dissertation.clothingshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
@EnableAsync
@EnableFeignClients
public class ClothingshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothingshopApplication.class, args);
	}

}
