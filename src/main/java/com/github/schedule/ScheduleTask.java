package com.github.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.services.PresenceService;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class ScheduleTask {
    private PresenceService service;

    /**
     * @param service
     */
    public ScheduleTask(PresenceService service) {
        this.service = service;
    }

    @Profile("prod")
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void checkTime() {
        log.info("Schedule start at {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss dd-mm-yyyy")));
        try {
            service.clickPresenceButton();
        } finally {
            log.info("Schedule finish at {}",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss dd-mm-yyyy")));
        }
    }
}
