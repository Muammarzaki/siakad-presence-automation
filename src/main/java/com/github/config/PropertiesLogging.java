package com.github.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class PropertiesLogging implements CommandLineRunner {

    /**
     * @param env
     */
    public PropertiesLogging(Environment env) {
        this.env = env;
    }

    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        log.info("Username {} Password {}  ", env.getProperty("user-config.username"),
                java.util.Optional.of(env.getProperty("user-config.password")).orElseGet(() -> "")
                        .replaceAll("[a-zA-Z0-9].", "*"));
    }

}
