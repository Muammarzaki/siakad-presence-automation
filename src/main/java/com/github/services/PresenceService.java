package com.github.services;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class PresenceService implements IPresenceService {

    private WebDriver driver;

    /**
     * @param driver
     * @param cookies
     */
    public PresenceService(WebDriver driver) {
        this.driver = driver;
    }

    @Value("${user-config.username}")
    private String userName;

    @Value("${user-config.password}")
    private String userPassword;

    @Value("${siakad-config.login-url}")
    private String loginUrl;

    @Value("${siakad-config.home-url}")
    private String homeUrl;

    @Override
    public void clickPresenceButton() {
        try {
            login();
            log.info("Current url Now is {}", driver.getCurrentUrl());

            driver.navigate().to(homeUrl);

            log.info("Current url Now is {}", driver.getCurrentUrl());

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30l));
            wait.until(ExpectedConditions.textToBe(By.cssSelector("button[type=submit]"), "Absen"));

            if (!driver.getTitle().equals("Dashboard Mahasiswa"))
                throw new WebDriverException("driver not at 'Dashboard Mahasiswa'");

            WebElement submitButton = driver.findElement(By.cssSelector("button[type=submit]"));
            submitButton.click();

        } catch (WebDriverException e) {
            log.error("Presence fail couse {}", e.getRawMessage());
        } catch (RuntimeException e) {
            log.error("Presence fail couse {}", e.getMessage());
        }
    }

    private void login() throws WebDriverException {
        log.info("System trying login to Siakad");
        driver.get(loginUrl);
        if (driver instanceof ChromeDriver chromeDriver) {

            WebElement userNameInput = chromeDriver.findElement(By.cssSelector("input[name='username']"));
            WebElement passwordInput = chromeDriver.findElement(By.cssSelector("input[name='password']"));
            WebElement submitButton = chromeDriver.findElement(By.cssSelector("button[type='submit']"));

            userNameInput.sendKeys(userName);
            passwordInput.sendKeys(userPassword);

            if (userNameInput.getAttribute("value").equals("")) {
                throw new WebDriverException("Can't insert username field");
            }
            if (passwordInput.getAttribute("value").equals("")) {
                throw new WebDriverException("Can't insert password field");
            }

            submitButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(30l))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".preloader")));

            log.debug(chromeDriver.getCurrentUrl());

            log.info("Login success at Siakad");
        }
    }

}
