package com.hackaton.makemate.web.controller.event;

import com.hackaton.makemate.database.event.EventRepository;
import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.domain.event.EventType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/events")
@RestController
public class EventsController {
  private final EventRepository eventRepository;

  public EventsController(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @GetMapping("/general")
  public List<Event> getPublicEvents() {
    List<Event> events =
        eventRepository.findAll().stream()
            .filter(e -> e.getEventType() == EventType.PUBLIC)
            .toList();
    return events;
  }

  @GetMapping("/private")
  public List<Event> getPrivateEvents() {
    List<Event> events =
        eventRepository.findAll().stream()
            .filter(e -> e.getEventType() == EventType.PRIVATE)
            .toList();
    return events;
  }
}
