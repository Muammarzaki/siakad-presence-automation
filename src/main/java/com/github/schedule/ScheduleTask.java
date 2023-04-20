package com.github.schedule;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.services.IPresenceService;
import com.github.utils.JsonReader;
import com.github.utils.TimeStapRange;
import com.github.validators.ScheduleTimeValidators;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class ScheduleTask {

    private IPresenceService service;

    @Qualifier("ScheduleConfigJsonPath")
    private JsonReader jsonReader;

    /**
     * @param service
     */
    public ScheduleTask(IPresenceService service) {
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

    private boolean checkSchedule() throws IOException {
        LocalDate now = LocalDate.now();
        if (isThisDayNotSunday(now)) {
            JsonNode jadwalTime = jsonReader.readJson()
                    .get("jadwal")
                    .get(now.getDayOfWeek().toString().toLowerCase());
            if (jadwalTime.isNull() || !jadwalTime.isArray()) {
                return false;
            }
            if (jadwalTime.isArray()) {
                for (JsonNode jsonNode : jadwalTime) {
                    String[] timeRange = jsonNode.asText().split("-");
                    if (new TimeStapRange(timeRange[0], timeRange[1]).isNowMustClick())
                        return true;
                }
            } else if (jadwalTime.asText().matches(ScheduleTimeValidators.SCHEDULE_TIME_FORMAT)) {
                String[] timeRange = jadwalTime.asText().split("-");
                if (new TimeStapRange(timeRange[0], timeRange[1]).isNowMustClick())
                    return true;
            }
        }
        return false;

    }

    private boolean isThisDayNotSunday(LocalDate now) {
        return !now.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}