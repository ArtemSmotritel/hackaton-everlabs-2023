package com.hackaton.makemate.web.controller.event;

import com.google.common.net.HttpHeaders;
import com.hackaton.makemate.database.event.EventRepository;
import com.hackaton.makemate.domain.event.Type;
import java.util.List;

import com.hackaton.makemate.web.dto.event.eventDto;
import com.hackaton.makemate.web.dto.event.eventDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/events")
@RestController
public class EventsController {
  private final EventRepository eventRepository;
  private final eventDtoMapper eventMapper;

  public EventsController(EventRepository eventRepository, eventDtoMapper eventMapper) {
    this.eventRepository = eventRepository;
    this.eventMapper = eventMapper;
  }

  @GetMapping("/general")
  public List<eventDto> getPublicEvents() {
    List<eventDto> events;
    events = eventRepository.findAll().stream()
        .filter(e -> e.getEventType() == Type.PUBLIC).map(eventMapper::toDto)
        .toList();
    return events;
  }

  @GetMapping("/private")
  public List<eventDto> getPrivateEvents(@RequestHeader(HttpHeaders.AUTHORIZATION) Long id) {
    List<eventDto> events =
        eventRepository.findAll().stream()
            .filter(e -> e.getEventType() == Type.PRIVATE && e.getCreatedBy().getId() == id).map(eventMapper::toDto)
            .toList();
    return events;
  }
}
