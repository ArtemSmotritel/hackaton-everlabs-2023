package com.hackaton.makemate.domain.event.impl;

import com.github.javafaker.Faker;
import com.google.common.collect.Sets;
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
import java.time.LocalDateTime;
import java.util.*;
import org.apache.commons.lang3.tuple.Pair;
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
  // I KNOW THAT IT SHOULDN'T BE HERE, CHILL
  private final Set<Pair<User, User>> pairs = new HashSet<>();

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

    event.getParticipants().remove(user);
    eventRepository.save(event);

    return toResponse(event, user);
  }

  @Override
  public List<Event> createPairedEvents() {
    final Faker faker = new Faker();
    final List<User> users = userRepository.findAll();
    final List<Event> createdEvents = new ArrayList<>();
    final Set<Pair<User, User>> newPairs = new HashSet<>();

    for (int i = 0; i < users.size(); i++) {
      for (int j = i + 1; j < users.size(); j++) {
        User u1 = users.get(i);
        User u2 = users.get(j);

        if (u1.getMatches().contains(u2) && u2.getMatches().contains(u1)) {
          newPairs.add(Pair.of(u1, u2));
        }
      }
    }

    for (var pair : pairs) {
      // SHIT CODE EDITION BUT USERS STORED HERE CANT BE COMPARED
      newPairs.removeIf(
          p ->
              p.getRight().getId().equals(pair.getRight().getId())
                  && p.getLeft().getId().equals(pair.getLeft().getId()));
    }

    for (var pair : newPairs) {
      Event event =
          new Event(
              null,
              String.format(
                  "Generated %s for %s and %s",
                  faker.witcher().location(), pair.getLeft().getLastName(), pair.getRight().getLastName()),
              faker.witcher().quote(),
              LocalDateTime.now(),
              faker.witcher().location(),
              null,
              EvenType.PRIVATE);
      event.setParticipants(Sets.newHashSet(pair.getLeft(), pair.getRight()));
      eventRepository.save(event);
      createdEvents.add(event);
    }

    pairs.addAll(newPairs);

    return createdEvents;
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
