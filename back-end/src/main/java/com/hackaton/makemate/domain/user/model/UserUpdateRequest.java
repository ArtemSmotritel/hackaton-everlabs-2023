package com.hackaton.makemate.domain.user.model;

import java.util.List;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        List<Long> interests) {
}
