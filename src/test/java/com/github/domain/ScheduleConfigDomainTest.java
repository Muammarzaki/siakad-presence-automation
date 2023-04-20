package com.github.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.helpers.ScheduleConfigJsonPath;
import com.github.utils.JsonReader;
import com.github.utils.JsonWriter;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Tag("validation scheduleConfigDomain Test")
public class ScheduleConfigDomainTest {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    private static ScheduleConfigDomain domain1;
    private static ScheduleConfigDomain domain2;

    @Test
    void TestRightDataToValidate() {
        Set<ConstraintViolation<ScheduleConfigDomain>> validate = validator.validate(ScheduleConfigDomainTest.domain1);
        assertThat(validate).isEmpty();
    }

    @Test
    void TestWrongDataToValidate() {
        Set<ConstraintViolation<ScheduleConfigDomain>> validate = validator.validate(ScheduleConfigDomainTest.domain2);
        assertThat(validate).isNotNull().isNotEmpty().hasSize(3);
        assertThat(validate.stream().map((vio) -> vio.getMessage()).toList()).hasSize(3)
                .containsExactlyInAnyOrder("The array must have exactly two elements", "Schedule time format not valid",
                        "Day is invalid try to check your calender");

    }

    @Nested
    @Tag("Try to convert SchduleConfigDomain to json ")
    public class ConvertDomainToJsonTest {
        ScheduleConfigJsonPath jsonPath = spy(new ScheduleConfigJsonPath());

        JsonWriter jsonWriter = jsonPath;
        JsonReader jsonReader = jsonPath;

        private final Path scheduleConfigPath = Path.of("schedule-config-test.json");
        String jsonExpencted;

        @Test
        void converToJson() {
            assertDoesNotThrow(() -> {
                JsonNode jsonReadResult;

                jsonWriter.writeJsonFile(domain1);

                jsonReadResult = jsonReader
                        .readJson();
                verify(jsonPath, times(2)).getJsonPath();

                assertThat(jsonReader.getJsonPath())
                        .isEqualTo(jsonWriter.getJsonPath())
                        .isEqualTo(scheduleConfigPath);
                verify(jsonPath, times(4)).getJsonPath();

                assertThat(jsonReadResult.toString())
                        .isNotNull()
                        .isNotEmpty()
                        .isNotBlank()
                        .isEqualTo(jsonExpencted);
            });

        }

        @Test
        void convertFromJson() {
            assertDoesNotThrow(() -> {
                jsonWriter.writeJsonFile(domain1);
                if (jsonReader.readJson(domain1.getClass()) instanceof ScheduleConfigDomain domainReader) {
                    assertThat(domainReader)
                            .isNotNull()
                            .isInstanceOf(domain1.getClass())
                            .isEqualTo(domain1);
                    assertThat(validator.validate(domainReader)).isEmpty();
                }
            });
        }

        @BeforeEach
        void setUp() {
            try {
                Files.createFile(scheduleConfigPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jsonExpencted = new ObjectMapper().writeValueAsString(domain1);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            when(jsonPath.getJsonPath()).thenReturn(scheduleConfigPath);
        }

        @AfterEach
        void tearDown() {
            try {
                Files.deleteIfExists(scheduleConfigPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @BeforeAll
    final static void beforeClass() {
        Map<String, java.util.List<String>> jadwal1 = new HashMap<>();

        jadwal1.put("monday", List.of("10:20-12:55", "07:45-09:25"));
        jadwal1.put("tuesday", List.of("08:35-10:20"));
        jadwal1.put("wednesday", List.of("08:35-11:10", "14:00-15:40"));
        jadwal1.put("thursday", List.of("14:00-16:20", "07:35-10:20"));
        jadwal1.put("friday", List.of("08:35-11:10"));
        jadwal1.put("saturday", List.of("09:30-11:30"));

        Map<String, java.util.List<String>> jadwal2 = new HashMap<>();

        jadwal2.put("onday", List.of("10:20-12:55", "07:45-09:25"));
        jadwal2.put("tuesday", List.of("08:35-10:20"));
        jadwal2.put("wednesday", List.of("08:35-11:10", "14:00-15:40"));
        jadwal2.put("thursday", List.of("14:00-16:2", "07:35-10:20"));
        jadwal2.put("friday", List.of("08:35-11:10"));
        jadwal2.put("saturday", List.of("09:30-11:30"));

        domain1 = new ScheduleConfigDomain(jadwal1, Collections.emptyList(), new int[] { 1, 30 });
        domain2 = new ScheduleConfigDomain(jadwal2, Collections.emptyList(), new int[1]);
    }
}
