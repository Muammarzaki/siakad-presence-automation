package com.github.schedule;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.services.PresenceService;
import com.github.utils.JsonReader;
import com.github.utils.TimeStapRange;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
@ConfigurationProperties
public class ScheduleTask {

    private PresenceService service;

    @Value("${schedule-config}")
    private Path scheduleConfig;

    /**
     * @param service
     */
    public ScheduleTask(PresenceService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    public void checkTime() throws IOException {
        if (checkSchedule()) {
            try {
                log.info("Schedule start at {}",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss dd-mm-yyyy")));
                service.clickPresenceButton();
            } finally {
                log.info("Schedule finish at {}",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss dd-mm-yyyy")));
            }
        }
    }

    private JsonReader jsonReader = () -> scheduleConfig;

    private boolean checkSchedule() throws IOException {
        JsonNode jadwalTime = jsonReader.readJson()
                .get("jadwal")
                .get(LocalDate.now().getDayOfWeek().toString().toLowerCase());
        if (jadwalTime.isArray()) {
            for (JsonNode jsonNode : jadwalTime) {
                String[] timeRange = jsonNode.asText().split("-");
                if (new TimeStapRange(timeRange[0], timeRange[1]).isNowMustClick())
                    return true;
            }
        }
        return false;
    }
}
