package com.hackaton.makemate.web.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackaton.makemate.web.dto.user.UserPreviewDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventDto(
    Long id, String name, String date, Integer matchesCount, UserPreviewDto createdBy) {}
