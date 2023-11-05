package com.hackaton.makemate.web.controller.event;

import com.google.common.net.HttpHeaders;
import com.hackaton.makemate.database.event.EventRepository;
import com.hackaton.makemate.domain.event.EvenType;
import com.hackaton.makemate.web.dto.event.EventDto;
import com.hackaton.makemate.web.dto.event.EventMapper;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/events")
@RestController
public class EventsController {
  private final EventRepository eventRepository;
  private final EventMapper eventMapper;

  public EventsController(EventRepository eventRepository, EventMapper eventMapper) {
    this.eventRepository = eventRepository;
    this.eventMapper = eventMapper;
  }

  @GetMapping("/general")
  public List<EventDto> getPublicEvents() {
    List<EventDto> events;
    events =
        eventRepository.findAll().stream()
            .filter(e -> e.getType() == EvenType.PUBLIC)
            .map(eventMapper::toDto)
            .toList();
    return events;
  }

  @GetMapping("/private")
  public List<EventDto> getPrivateEvents(@RequestHeader(HttpHeaders.AUTHORIZATION) Long id) {
    List<EventDto> events =
        eventRepository.findAll().stream()
            .filter(e -> e.getType() == EvenType.PRIVATE && e.getCreatedBy().getId() == id)
            .map(eventMapper::toDto)
            .toList();
    return events;
  }
}
