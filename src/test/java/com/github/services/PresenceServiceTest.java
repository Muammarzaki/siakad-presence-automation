package com.github.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class PresenceServiceTest {

    @Autowired
    private PresenceService service;

    @SpyBean
    private WebDriver webDriver;

    @Test
    void testPresence() {
        service.clickPresenceButton();
        assertThat(webDriver.manage().getCookies()).isNotNull().isNotEmpty();
        webDriver.manage().getCookies().stream().forEach(System.out::println);
    }
}
