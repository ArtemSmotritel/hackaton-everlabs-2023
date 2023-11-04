package com.hackaton.makemate.web.dto.user;

import com.hackaton.makemate.web.dto.interest.InterestDto;

import java.util.List;

public record UserPreviewDto(
        Long id,
        String firstName,
        String lastName,
        Integer age,
        List<InterestDto> interests,
        String avatarUrl
) {
}
