package com.hackaton.makemate.web.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackaton.makemate.web.dto.interest.InterestDto;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserPreviewDto(
    Long id,
    String firstName,
    String lastName,
    Integer age,
    List<InterestDto> interests,
    String avatarUrl,
    String description) {}
