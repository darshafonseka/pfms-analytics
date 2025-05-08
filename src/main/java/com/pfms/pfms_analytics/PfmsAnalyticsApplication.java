package com.pfms.pfms_analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PfmsAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfmsAnalyticsApplication.class, args);
	}

}
