package com.github.helpers;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.domain.ScheduleConfigDomain;
import com.github.utils.JsonReader;
import com.github.utils.JsonWriter;

@Component("scheduleConfigJsonPath")
public class ScheduleConfigJsonPath implements JsonWriter, JsonReader {

    public static final Class<ScheduleConfigDomain> TYPE = ScheduleConfigDomain.class;

    @Value("${schedule-config}")
    private Path scheduleConfig;

    @Override
    public Path getJsonPath() {
        return this.scheduleConfig;
    }

    @Override
    public boolean writeJsonFile(Object jsonNode) throws IOException {
        return JsonWriter.super.writeJsonFile(jsonNode);
    }

    @Override
    public Object readJson(Class<?> type) throws IOException {
        return JsonReader.super.readJson(type);
    }
}
