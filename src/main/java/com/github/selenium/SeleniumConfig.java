package com.github.selenium;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "selenium")
public class SeleniumConfig {

    private boolean isVerbose;

    @Value("args:")
    private String args;

    @Bean(destroyMethod = "quit")
    @Primary
    public WebDriver webDriver(ChromeDriverService chromeDriverService) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(args);
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setPageLoadTimeout(Duration.ofMinutes(1l));
        return new ChromeDriver(chromeDriverService, chromeOptions);
    }

    @Bean
    public ChromeDriverService chromeDriverService() {
        return new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withWhitelistedIps("")
                .withVerbose(isVerbose)
                .build();
    }
}
