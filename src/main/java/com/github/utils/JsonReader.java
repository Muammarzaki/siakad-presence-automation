package com.github.utils;

import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JsonReader
 */
public interface JsonReader extends JsonPath {

    public default JsonNode readJson() throws IOException {
        Path jsonfile = getJsonPath();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonfile.toFile());
    }
}