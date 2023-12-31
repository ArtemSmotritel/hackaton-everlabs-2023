package com.hackaton.makemate.web.dto.event;

import com.hackaton.makemate.domain.event.EvenType;
import com.hackaton.makemate.domain.event.EventResponse;
import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.web.dto.interest.InterestDto;
import com.hackaton.makemate.web.dto.interest.InterestMapper;
import com.hackaton.makemate.web.dto.user.UserMapper;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
  private final UserMapper userMapper;
  private final InterestMapper interestMapper;

  public EventMapper(UserMapper userMapper, InterestMapper interestMapper) {
    this.userMapper = userMapper;
    this.interestMapper = interestMapper;
  }

  public SimplifiedEventDto toSimplifiedDto(EventResponse entity) {
    if (entity == null) return null;

    Set<InterestDto> interests = null;
    if (entity.event().getType().equals(EvenType.PRIVATE)) interests = processInterests(entity);
    else interests = new HashSet<>(interestMapper.toDto(entity.event().getInterests()));

    return new SimplifiedEventDto(
        entity.event().getId(),
        entity.event().getName(),
        entity.event().getDateString(),
        interests,
        entity.matchesCount(),
        entity.accepted());
  }

  public List<SimplifiedEventDto> toSimplifiedDto(Collection<EventResponse> entities) {
    if (entities == null) return null;

    return entities.stream().map(this::toSimplifiedDto).toList();
  }

  public EventDto toDto(EventResponse entity) {
    if (entity == null) return null;

    Set<InterestDto> interests = null;
    if (entity.event().getType().equals(EvenType.PRIVATE)) interests = processInterests(entity);
    else interests = new HashSet<>(interestMapper.toDto(entity.event().getInterests()));

    return new EventDto(
        entity.event().getId(),
        entity.event().getName(),
        entity.event().getDateString(),
        interests,
        entity.event().getDescription(),
        entity.event().getPlace(),
        userMapper.toDto(entity.event().getCreatedBy()),
        userMapper.toDto(entity.event().getParticipants()),
        entity.matchesCount(),
        entity.accepted());
  }

  public List<EventDto> toDto(List<EventResponse> entities) {
    if (entities == null) return null;

    return entities.stream().map(this::toDto).toList();
  }

  private Set<InterestDto> processInterests(EventResponse entity) {
    return new HashSet<>(
        entity.event().getAllUsers().stream()
            .map(User::getInterests)
            .map(interestMapper::toDto)
            .reduce(
                (identity, acc) -> {
                  Set<InterestDto> res = new HashSet<>(identity);
                  res.retainAll(acc);
                  return new ArrayList<>(res);
                })
            .orElse(List.of()));
  }
}
