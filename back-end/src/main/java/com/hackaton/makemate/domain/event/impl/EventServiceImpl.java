package com.hackaton.makemate.domain.event.impl;

import com.hackaton.makemate.database.event.EventRepository;
import com.hackaton.makemate.database.user.UserRepository;
import com.hackaton.makemate.domain.event.EvenType;
import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.domain.event.EventService;
import com.hackaton.makemate.domain.exception.BadRequestException;
import com.hackaton.makemate.domain.exception.ForbiddenException;
import com.hackaton.makemate.domain.user.User;
import java.util.List;

public class EventServiceImpl implements EventService {
  private final EventRepository eventRepository;
  private final UserRepository userRepository;

  public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Event getEventById(Long userId, Long eventId) {
    Event event = fetchById(eventId);

    if (event.getType() == EvenType.PRIVATE
        && event.getAllUsers().stream().anyMatch(u -> u.getId().equals(userId))) {
      throw new ForbiddenException(
          "You can't fetch information about this event you little fraud ^^");
    }

    return event;
  }

  @Override
  public List<Event> getAvailablePublicEvents(Long userId) {
    return eventRepository.findAll().stream().filter(e -> e.getType() == EvenType.PUBLIC).toList();
  }

  @Override
  public List<Event> getAvailablePrivateEvents(Long userId) {
    return eventRepository.findAll().stream()
        .filter(e -> e.getType() == EvenType.PRIVATE)
        .filter(e -> e.getAllUsers().stream().anyMatch(u -> u.getId().equals(userId)))
        .toList();
  }

  @Override
  public Event attendEventById(Long userId, Long eventId) {
    User user = fetchUserById(userId);
    Event event = fetchById(eventId);

    if (event.getCreatedBy().equals(user))
      throw new BadRequestException("MAN you are creator stop playing with me");

    if (event.getType() == EvenType.PRIVATE) {
      throw new BadRequestException("No i don't think so, this is some private event");
    }

    event.getParticipants().add(user);

    eventRepository.save(event);

    return event;
  }

  @Override
  public Event skipEventById(Long userId, Long eventId) {
    User user = fetchUserById(userId);
    Event event = fetchById(eventId);

    event.getParticipants().remove(user);

    eventRepository.save(event);

    return event;
  }

  private Event fetchById(Long eventId) {
    return eventRepository
        .findById(eventId)
        .orElseThrow(
            () ->
                new BadRequestException(String.format("Event with id %s doesn't exist", eventId)));
  }

  private User fetchUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () -> new BadRequestException(String.format("User with id %s doesn't exist", userId)));
  }
}
