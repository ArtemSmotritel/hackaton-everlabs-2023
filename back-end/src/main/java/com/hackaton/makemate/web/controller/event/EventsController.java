package com.hackaton.makemate.web.controller.event;

import com.google.common.net.HttpHeaders;
import com.hackaton.makemate.domain.event.EventService;
import com.hackaton.makemate.web.dto.event.EventDto;
import com.hackaton.makemate.web.dto.event.EventMapper;
import com.hackaton.makemate.web.dto.event.SimplifiedEventDto;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/events")
@RestController
public class EventsController {
  private final EventService eventService;
  private final EventMapper eventMapper;

  public EventsController(EventService eventService, EventMapper eventMapper) {
    this.eventService = eventService;
    this.eventMapper = eventMapper;
  }

  @GetMapping("/{eventId}")
  public EventDto eventById(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
      @PathVariable("eventId") Long eventId) {
    return eventMapper.toDto(eventService.getEventById(userId, eventId));
  }

  @PostMapping("/{eventId}/attend")
  public EventDto attendEventById(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
      @PathVariable("eventId") Long eventId) {
    return eventMapper.toDto(eventService.attendEventById(userId, eventId));
  }

  @PostMapping("/{eventId}/skip")
  public EventDto skipEventById(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
      @PathVariable("eventId") Long eventId) {
    return eventMapper.toDto(eventService.skipEventById(userId, eventId));
  }

  @GetMapping("/public")
  public List<SimplifiedEventDto> publicEvents(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Long userId) {
    return eventMapper.toSimplifiedDto(eventService.getAvailablePublicEvents(userId));
  }

  @GetMapping("/private")
  public List<SimplifiedEventDto> privateEvents(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Long userId) {
    return eventMapper.toSimplifiedDto(eventService.getAvailablePrivateEvents(userId));
  }
}
