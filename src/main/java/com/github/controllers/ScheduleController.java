package com.github.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.domain.ResponseDomain;
import com.github.domain.ScheduleConfigDomain;
import com.github.helpers.ScheduleConfigJsonPath;

import jakarta.validation.Valid;

@RestController
@RequestMapping("schedule-config")
public class ScheduleController {

  @Qualifier("ScheduleConfigJsonPath")
  private ScheduleConfigJsonPath jsonSchedule;

  @PostMapping("change")
  public ResponseEntity<Object> changeConfiguration(@Valid @RequestBody ScheduleConfigDomain body) throws IOException {
    jsonSchedule.writeJsonFile(body);
    return ResponseEntity.accepted().body(new ResponseDomain.Builder()
        .setStatus(HttpStatus.ACCEPTED)
        .setStatusCode(201)
        .build(body));
  }

  @PostMapping("add-free-day")
  public ResponseEntity<Object> addLiburTime(@RequestBody List<Integer> freeDay) throws IOException {
    Class<ScheduleConfigDomain> type = ScheduleConfigJsonPath.TYPE;
    boolean proses = java.util.Optional.ofNullable(jsonSchedule.readJson(type))
        .map(type::cast)
        .filter(type::isInstance)
        .map(t -> {
          try {
            return jsonSchedule.writeJsonFile(t);
          } catch (IOException e) {
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
