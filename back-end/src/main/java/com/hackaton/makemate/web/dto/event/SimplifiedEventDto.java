package com.hackaton.makemate.web.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackaton.makemate.web.dto.interest.InterestDto;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SimplifiedEventDto(
    Long id,
    String name,
    String date,
    Collection<InterestDto> interests,
    Integer matchesCount,
    boolean accepted) {}
