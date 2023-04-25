package com.github.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.domain.ResponseDomain;
import com.github.domain.ScheduleConfigDomain;
import com.github.helpers.ScheduleConfigJsonPath;
import com.github.utils.JsonReader;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("schedule-config")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleConfigJsonPath jsonSchedule;

  @GetMapping("config")
  public ResponseEntity<Object> getConfig() throws IOException {
    final JsonReader jsonReader = jsonSchedule;
    return ResponseEntity.ok().body(jsonReader.readJson(ScheduleConfigJsonPath.TYPE));
  }

  @PostMapping("change")
  public ResponseEntity<Object> changeConfiguration(@Valid @RequestBody final ScheduleConfigDomain body)
      throws IOException {
    jsonSchedule.writeJsonFile(body);
    return ResponseEntity.accepted().body(new ResponseDomain.Builder()
        .setStatus(HttpStatus.ACCEPTED)
        .setStatusCode(201)
        .build(body));
  }

  @PostMapping("add-freeday")
  public ResponseEntity<Object> addLiburTime(@RequestBody final List<String> freeDay) throws IOException {
    final Class<ScheduleConfigDomain> type = ScheduleConfigJsonPath.TYPE;
    final boolean proses = java.util.Optional.ofNullable(jsonSchedule.readJson(type))
        .map(type::cast)
        .filter(type::isInstance)
        .map(item -> {
          item.setFreeDayOfMonth(freeDay);
          return item;
        })
        .map(t -> {
          try {
            return jsonSchedule.writeJsonFile(t);
          } catch (final IOException e) {
            return false;
          }
        })
        .orElseThrow(() -> new ClassCastException("Cant cast configuration to ScheduleConfigDomain"));

    if (!proses)
      throw new IOException("Fail to write in schedule config file");

    return ResponseEntity.ok().body(new ResponseDomain.Builder()
        .setStatus(HttpStatus.OK)
        .setStatusCode(200)
        .build(proses));
  }
}
