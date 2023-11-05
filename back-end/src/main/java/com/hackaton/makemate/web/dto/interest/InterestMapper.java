package com.hackaton.makemate.web.dto.interest;

import com.hackaton.makemate.domain.interest.Interest;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InterestMapper {
  public InterestDto toDto(Interest entity) {
    if (entity == null) return null;
    return new InterestDto(entity.getId(), entity.getName());
  }

  public List<InterestDto> toDto(List<Interest> entities) {
    return entities.stream().map(this::toDto).toList();
  }
}
