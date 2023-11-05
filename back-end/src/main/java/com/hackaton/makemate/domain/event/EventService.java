package com.hackaton.makemate.domain.event;

import java.util.List;

public interface EventService {
  EventResponse getEventById(Long userId, Long eventId);

  List<EventResponse> getAvailablePublicEvents(Long userId);

  List<EventResponse> getAvailablePrivateEvents(Long userId);

  EventResponse attendEventById(Long userId, Long eventId);

  EventResponse skipEventById(Long userId, Long eventId);

  List<Event> createPairedEvents();
}
