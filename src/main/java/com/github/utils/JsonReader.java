package com.github.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JsonReader
 */
public interface JsonReader extends JsonPath {

    public default Object readJson(Class<?> type) throws IOException {
        Path jsonfile = getJsonPath();
        try (BufferedReader reader = Files.newBufferedReader(jsonfile)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(reader, type);
        }
    }

    public default JsonNode readJson() throws IOException {
        Path jsonfile = getJsonPath();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonfile.toFile());
    }
}