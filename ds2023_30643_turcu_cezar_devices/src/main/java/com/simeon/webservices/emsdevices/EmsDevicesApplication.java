package com.simeon.webservices.emsdevices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EmsDevicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsDevicesApplication.class, args);
	}

}
