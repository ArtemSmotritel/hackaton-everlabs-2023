package com.hackaton.makemate.web.dto.user;

import java.util.List;

public record UserUpdateRequestDto(String firstName, String lastName, List<Long> interests) {}
