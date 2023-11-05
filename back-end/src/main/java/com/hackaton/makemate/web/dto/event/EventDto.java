package com.hackaton.makemate.web.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackaton.makemate.web.dto.interest.InterestDto;
import com.hackaton.makemate.web.dto.user.UserPreviewDto;
import java.util.Collection;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventDto(
    Long id,
    String name,
    String date,
    Collection<InterestDto> interests,
    String description,
    String place,
    UserPreviewDto createdBy,
    List<UserPreviewDto> participants,
    Integer matchCount,
    boolean accepted) {}
