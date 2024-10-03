package com.simeon.webservices.emssensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmsSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsSensorApplication.class, args);
	}

}
