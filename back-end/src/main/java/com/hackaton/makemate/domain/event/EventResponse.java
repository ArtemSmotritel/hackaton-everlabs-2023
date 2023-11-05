package com.hackaton.makemate.domain.event;

public record EventResponse(Event event, int matchesCount, boolean accepted) {}
