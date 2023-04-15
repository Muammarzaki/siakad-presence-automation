package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class SiakadPresenceAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiakadPresenceAutomationApplication.class, args);
	}

}
