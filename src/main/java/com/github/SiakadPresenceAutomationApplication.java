package com.github;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@EnableConfigurationProperties
@SpringBootApplication
public class SiakadPresenceAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiakadPresenceAutomationApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner starup(WebDriver webDriver) {
		URL resource = this.getClass().getClassLoader().getResource("template/debug.html");
		return args -> webDriver.get(resource.toString());
	}
}
