package com.hackaton.makemate.web.dto.user;

import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.domain.user.model.UserMatchRequest;
import com.hackaton.makemate.web.dto.interest.InterestMapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  private final InterestMapper interestMapper;

  public UserMapper(InterestMapper interestMapper) {
    this.interestMapper = interestMapper;
  }

  public UserPreviewDto toDto(User entity) {
    if (entity == null) return null;

    return new UserPreviewDto(
        entity.getId(),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getAge(),
        interestMapper.toDto(entity.getInterests()),
        entity.getAvatarUrl(),
        entity.getDescription());
  }

  public UserMatchRequest toEntity(UserMatchRequestDto dto) {
    if (dto == null) return null;

    return new UserMatchRequest(dto.userId(), dto.matchId());
  }

  public List<UserPreviewDto> toDto(List<User> entities) {
    return entities.stream().map(this::toDto).toList();
  }
}
