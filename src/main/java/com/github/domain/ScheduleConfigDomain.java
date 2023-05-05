package com.github.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.validators.annotations.DateFormatter;
import com.github.validators.annotations.DayofWeek;
import com.github.validators.annotations.RightTimescheduleFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ScheduleConfigDomain {

    @JsonProperty("timetable")
    @NotNull
    @Size(min = 6, max = 7)
    private Map<@DayofWeek String, List<@RightTimescheduleFormat String>> dayWithScheduleTime;

    @JsonProperty("free-day")
    private List<@DateFormatter String> freeDayOfMonth;

    @JsonProperty("range")
    @Size(min = 2, max = 2, message = "The array must have exactly two elements")
    @NotNull
    @NotEmpty
    private List<@DateFormatter String> rangeScheduleOfDay;

}
