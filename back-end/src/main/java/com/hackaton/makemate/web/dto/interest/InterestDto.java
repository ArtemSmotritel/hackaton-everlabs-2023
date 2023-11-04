package com.hackaton.makemate.web.dto.interest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InterestDto(Long id, String name) {}
