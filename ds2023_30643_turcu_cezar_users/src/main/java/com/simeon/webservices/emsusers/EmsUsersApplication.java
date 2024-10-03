package com.simeon.webservices.emsusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EmsUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsUsersApplication.class, args);
	}

}
