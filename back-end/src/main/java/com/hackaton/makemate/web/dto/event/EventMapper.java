package com.hackaton.makemate.web.dto.event;

import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.web.dto.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
  private final UserMapper userMapper;

  public EventMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public EventDto toDto(Event entity) {
    if (entity == null) return null;

    return new EventDto(
        entity.getId(),
        entity.getName(),
        entity.getDateString(),
        entity.getCurrentMatchCount(),
        userMapper.toDto(entity.getCreatedBy()));
  }
}
