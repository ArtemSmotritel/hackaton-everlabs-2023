package com.hackaton.makemate.web.dto.user;

import java.util.List;

record UpdateUserRequestDto(String firstName, String lastName, List<Long> interests) {}
