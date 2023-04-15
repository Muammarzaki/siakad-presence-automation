package com.github.selenium;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "selenium")
@Component
public class SeleniumProperties {

    private boolean verbose = false;

    private long timeout = 1;

    private List<String> args = Collections.emptyList();

    private boolean headless = false;

}
