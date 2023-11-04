package com.hackaton.makemate.web.dto.user;

import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.domain.user.model.UserMatchRequest;
import com.hackaton.makemate.web.dto.interest.InterestMapper;
import java.util.Calendar;
import java.util.Date;
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
        entity.getAvatarUrl());
  }

  public UserMatchRequest toEntity(UserMatchRequestDto dto) {
    if (dto == null) return null;

    return new UserMatchRequest(dto.userId(), dto.matchId());
  }

  public List<UserPreviewDto> toDto(List<User> entities) {
    return entities.stream().map(this::toDto).toList();
  }

  private Integer birthDateToAge(Date birthDay) {
    if (birthDay == null) return null;

    Calendar today = Calendar.getInstance();

    Calendar birthDate = Calendar.getInstance();
    birthDate.setTime(birthDay);

    int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

    if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
      age--;
    }

    return age;
  }
}
