package com.hackaton.makemate.domain.event.impl;

import com.hackaton.makemate.database.event.EventRepository;
import com.hackaton.makemate.database.user.UserRepository;
import com.hackaton.makemate.domain.event.EvenType;
import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.domain.event.EventResponse;
import com.hackaton.makemate.domain.event.EventService;
import com.hackaton.makemate.domain.exception.BadRequestException;
import com.hackaton.makemate.domain.exception.ForbiddenException;
import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.domain.user.UserMatcher;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final UserMatcher userMatcher;
  private final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
  private final Random random = new SecureRandom();

  public EventServiceImpl(
      EventRepository eventRepository, UserRepository userRepository, UserMatcher userMatcher) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.userMatcher = userMatcher;
  }

  @Override
  public EventResponse getEventById(Long userId, Long eventId) {
    User user = fetchUserById(userId);
    Event event = fetchById(eventId);

    if (event.getType() == EvenType.PRIVATE
        && event.getAllUsers().stream().noneMatch(u -> u.getId().equals(userId))) {
      throw new ForbiddenException(
          "You can't fetch information about this event you little fraud ^^");
    }

    return toResponse(event, user);
  }

  @Override
  public List<EventResponse> getAvailablePublicEvents(Long userId) {
    User user = fetchUserById(userId);
    List<Event> events =
        eventRepository.findAll().stream().filter(e -> e.getType() == EvenType.PUBLIC).toList();

    return events.stream().map(e -> this.toResponse(e, user)).toList();
  }

  @Override
  public List<EventResponse> getAvailablePrivateEvents(Long userId) {
    User user = fetchUserById(userId);
    return eventRepository.findAll().stream()
        .filter(e -> e.getType() == EvenType.PRIVATE)
        // YES, YES I KNOW THAT, I WASTE MEMORY AND TIME, BUT IT'S BETTER THAT SHIT-ASS PREDICATE
        .filter(e -> e.getAllUsers().stream().anyMatch(u -> u.getId().equals(userId)))
        .map(e -> toResponse(e, user))
        .toList();
  }

  @Override
  public EventResponse attendEventById(Long userId, Long eventId) {
    User user = fetchUserById(userId);
    Event event = fetchById(eventId);

    if (event.getCreatedBy().equals(user))
      throw new BadRequestException("MAN you are creator stop playing with me");

    if (event.getType() == EvenType.PRIVATE) {
      throw new BadRequestException("No i don't think so, this is some private event");
    }

    event.getParticipants().add(user);

    eventRepository.save(event);

    return toResponse(event, user);
  }

  @Override
  public EventResponse skipEventById(Long userId, Long eventId) {
    User user = fetchUserById(userId);
    Event event = fetchById(eventId);

    if (event.getCreatedBy().equals(user)) {
      eventRepository.delete(event);
      return toResponse(event, user);
    }

    event.getParticipants().remove(user);

    eventRepository.save(event);

    return toResponse(event, user);
  }

  @Override
  public EventResponse createRandomPrivateEvent() {
    long totalCount = userRepository.count();

    if (totalCount < 2) {
      logger.warn("Now enough users to create private event");
      return null;
    }

    User sender = fetchUserById(1 + random.nextLong(totalCount));

    int participantsSize = 1 + random.nextInt((int) Math.min(10, totalCount - 2));

    List<User> matchingUsers = userMatcher.matchAlgorithm(sender, userRepository.findAll());

    for (int i = 0; i < participantsSize; i++) {}

    return null;
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

  private EventResponse toResponse(Event event, User user) {
    int matchCount;
    boolean accepted;

    Set<User> participants = event.getAllUsers();

    if (participants == null || participants.isEmpty()) {
      matchCount = 0;
      accepted = false;
    } else {
      Set<User> temp = new HashSet<>(participants);
      temp.retainAll(user.getAllUsersView());
      matchCount = temp.size();

      accepted = participants.contains(user) || event.getCreatedBy().equals(user);
    }
    return new EventResponse(event, matchCount, accepted);
  }
}
