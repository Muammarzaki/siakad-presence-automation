package com.github.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * TimeStapSchedule
 */
public record TimeStapRange(LocalTime start, LocalTime end) {

    public TimeStapRange(String start, String end) {
        this(LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm")),
                LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm")));
    }

    public boolean isNowMustClick() {
        LocalTime now = LocalTime.now();
        return now.isAfter(this.start) && now.isBefore(this.end);
    }
}