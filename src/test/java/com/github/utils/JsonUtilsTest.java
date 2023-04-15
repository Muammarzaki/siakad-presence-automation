package com.github.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// @ExtendWith(MockitoExtension.class)
public class JsonUtilsTest {
    java.nio.file.Path jsonPath = java.nio.file.Path.of("jsonfileTest.json");
    String value = """
            {
                "jadwal": {
                    "monday": [],
                    "tuesday": [],
                    "wednesday": [],
                    "thursday": [],
                    "friday": [],
                    "saturday": []
                },
                "libur": []
            }""";

    JsonWriter jsonWriter;

    JsonReader jsonReader;

    @Test
    void readAndWriteJsonfileTest() {
        assertDoesNotThrow(() -> {
            jsonWriter.writeJsonFile(value);
            com.fasterxml.jackson.databind.JsonNode valueJsonfile = jsonReader.readJson();
            assertThat(valueJsonfile.asText()).isNotNull()
                    .isNotEmpty()
                    .isNotEqualTo("{}")
                    .isNotEqualTo("")
                    .isEqualTo(value);
        });
    }

    @BeforeEach
    void setUp() {
        try {
            Files.createFile(jsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonWriter = () -> jsonPath;
        jsonReader = () -> jsonPath;
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(jsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
