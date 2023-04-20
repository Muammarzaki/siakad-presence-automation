package com.github.utils;

import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public interface JsonWriter extends JsonPath {

    public default boolean writeJsonFile(Object jsonNode) throws IOException {
        Path jsonFile = getJsonPath();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        objectWriter.writeValue(jsonFile.toFile(), jsonNode);
        
        return true;
    }
}
