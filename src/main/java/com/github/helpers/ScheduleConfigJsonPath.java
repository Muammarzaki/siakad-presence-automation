package com.github.helpers;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.domain.ScheduleConfigDomain;
import com.github.utils.JsonReader;
import com.github.utils.JsonWriter;

@Component
public class ScheduleConfigJsonPath implements JsonWriter, JsonReader {

    public static final Class<ScheduleConfigDomain> TYPE = ScheduleConfigDomain.class;

    @Value("${schedule-config}")
    private Path scheduleConfig;

    @Override
    public Path getJsonPath() {
        return this.scheduleConfig;
    }

}
