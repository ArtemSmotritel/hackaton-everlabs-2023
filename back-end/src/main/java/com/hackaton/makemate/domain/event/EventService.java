package com.hackaton.makemate.domain.event;

import java.util.List;

public interface EventService {
  Event getEventById(Long userId, Long eventId);

  List<Event> getAvailablePublicEvents(Long userId);

  List<Event> getAvailablePrivateEvents(Long userId);

  Event attendEventById(Long userId, Long eventId);

  Event skipEventById(Long userId, Long eventId);
}
