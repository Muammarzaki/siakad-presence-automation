package com.github.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.github.domain.ScheduleConfigDomain;
import com.github.helpers.ScheduleConfigJsonPath;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Tag("unit-testing")
public class ScheduleControllerTest {

    private ScheduleConfigJsonPath mockJsonSchedule;
    private ScheduleController scheduleController;
    private Validator validator;
    Map<String, java.util.List<String>> jadwal1 = new HashMap<>();
    private ScheduleConfigDomain domain1;
    Path fileName = Path.of("schedule-config.json");
    ArgumentCaptor<ScheduleConfigDomain> captor;
    String valueFile = """
            {
                "timetable": {
                    "monday": [
                        "10:20-12:55",
                        "07:45-09:25"
                    ],
                    "tuesday": [
                        "08:35-10:20"
                    ],
                    "wednesday": [
                        "08:35-11:10",
                        "14:00-15:40"
                    ],
                    "thursday": [
                        "14:00-16:20",
                        "07:35-10:20"
                    ],
                    "friday": [
                        "08:35-11:10"
                    ],
                    "saturday": [
                        "09:30-11:20"
                    ]
                },
                "free-day": [],
                "range": [
                    "2023-01-01",
                    "2023-12-31"
                ]
            }
                    """;

    @BeforeEach
    public void setUp() {
        captor = ArgumentCaptor.forClass(ScheduleConfigDomain.class);

        jadwal1.put("monday", List.of("10:20-12:55", "07:45-09:25"));
        jadwal1.put("tuesday", List.of("08:35-10:20"));
        jadwal1.put("wednesday", List.of("08:35-11:10", "14:00-15:40"));
        jadwal1.put("thursday", List.of("14:00-16:20", "07:35-10:20"));
        jadwal1.put("friday", List.of("08:35-11:10"));
        jadwal1.put("saturday", List.of("09:30-11:30"));

        domain1 = new ScheduleConfigDomain(jadwal1, Collections.emptyList(), List.of("2023-1-1", "2023-31-12"));

        mockJsonSchedule = mock(ScheduleConfigJsonPath.class);
        scheduleController = new ScheduleController(mockJsonSchedule);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        try {
            Files.createFile(fileName);
            BufferedWriter writer = Files.newBufferedWriter(fileName, StandardOpenOption.WRITE);
            writer.write(valueFile);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        when(mockJsonSchedule.getJsonPath()).thenReturn(fileName);

    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddLiburTime() {
        assertDoesNotThrow(() -> {
            when(mockJsonSchedule.readJson(ScheduleConfigDomain.class)).thenReturn(domain1);
            when(mockJsonSchedule.writeJsonFile(domain1)).thenReturn(true);

            List<String> freeDay = List.of("2023-1-2", "2023-1-1");
            scheduleController.addLiburTime(freeDay);

            verify(mockJsonSchedule, times(1)).readJson(any());
            verify(mockJsonSchedule, times(1)).writeJsonFile(captor.capture());

            ScheduleConfigDomain captorValue = captor.getValue();

            assertThat(captorValue.getFreeDayOfMonth())
                    .isNotNull()
                    .isNotEmpty()
                    .containsAll(freeDay);

            Set<ConstraintViolation<ScheduleConfigDomain>> validate = validator.validate(captorValue);
            assertThat(validate).isNullOrEmpty();
        });
    }

    @Test
    void testChangeConfiguration() {

    }

    @Test
    void testGetConfig() {

    }

    @Nested
    @Tag("integrated-testing")

    class ScheduleControllerIntegratedTest {

    }
}
