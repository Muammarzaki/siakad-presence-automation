package com.github.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.validators.annotations.DayofWeek;
import com.github.validators.annotations.RightTimescheduleFormat;

import jakarta.annotation.Nullable;
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

    @JsonProperty("jadwal")
    @NotNull
    @Size(min = 6, max = 7)
    private Map<@DayofWeek String, @Nullable List<@RightTimescheduleFormat String>> dayWithScheduleTime;

    @JsonProperty("libur")
    @Nullable
    private List<Integer> freeDayOfMount;

    @JsonProperty("batas")
    @Size(min = 2, max = 2, message = "The array must have exactly two elements")
    @NotNull
    @NotEmpty
    private int[] rangeScheduleOfDay;

}
