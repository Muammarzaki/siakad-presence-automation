package com.github.selenium;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "selenium")
public class SeleniumConfig {

    @Bean(destroyMethod = "quit")
    @Primary
    public WebDriver webDriver(ChromeDriverService chromeDriverService, SeleniumProperties properties) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (properties.getArgs() != null && !properties.getArgs().isEmpty()) {
            chromeOptions.addArguments(properties.getArgs());
        }
        chromeOptions.setHeadless(properties.isHeadless());
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.addArguments("--remote-allow-origins=*","--disable-ipc-flooding-protection");
        chromeOptions.setPageLoadTimeout(Duration.ofMinutes(properties.getTimeout()));
        return new ChromeDriver(chromeDriverService, chromeOptions);
    }

    @Bean
    public ChromeDriverService chromeDriverService(SeleniumProperties properties) {
        return new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withVerbose(properties.isVerbose())
                .build();
    }
}
