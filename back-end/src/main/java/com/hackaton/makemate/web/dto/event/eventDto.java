package com.hackaton.makemate.web.dto.event;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record eventDto (
    Long id,
    String name,
    Date dateOfEvent,
    Long matchesCount,
    Long createdBy
){};
