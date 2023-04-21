package com.github.schedule;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.domain.ScheduleConfigDomain;
import com.github.helpers.ScheduleConfigJsonPath;
import com.github.services.IPresenceService;
import com.github.utils.JsonReader;
import com.github.utils.TimeStapRange;
import com.github.validators.annotations.RightTimescheduleFormat;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class ScheduleTask {

    private IPresenceService service;

    @Qualifier("scheduleConfigJsonPath")
    private JsonReader jsonReader;

    /**
     * @param service
     * @param jsonReader
     */
    public ScheduleTask(IPresenceService service, ScheduleConfigJsonPath jsonReader) {
        this.service = service;
        this.jsonReader = jsonReader;
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
        final LocalDate now = LocalDate.now();
        if (isThisDayNotSunday(now)) {
            ScheduleConfigDomain scheduleConfigDomain = (ScheduleConfigDomain) jsonReader
                    .readJson(ScheduleConfigJsonPath.TYPE);
            @Nullable
            List<@RightTimescheduleFormat String> nowScheduleTime = scheduleConfigDomain.getDayWithScheduleTime()
                    .get(now.getDayOfWeek().toString().toLowerCase());

            boolean isfreeday = scheduleConfigDomain.getFreeDayOfMonth().stream()
                    .anyMatch(item -> this.checkItsFreeDayinThisMonth(item, now));

            if (nowScheduleTime.isEmpty() || isfreeday) {
                return false;
            }
            for (String timeConfig : nowScheduleTime) {
                String[] timeRange = timeConfig.split("-");
                if (new TimeStapRange(timeRange[0], timeRange[1]).isNowMustClick()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkItsFreeDayinThisMonth(String freeDay, LocalDate time) {
        String[] split = freeDay.split(":");
        return split[0].equalsIgnoreCase(String.valueOf(time.getDayOfMonth()))
                && split[2].equalsIgnoreCase(String.valueOf(time.getMonthValue()));
    }

    private boolean isThisDayNotSunday(LocalDate now) {
        return !now.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}