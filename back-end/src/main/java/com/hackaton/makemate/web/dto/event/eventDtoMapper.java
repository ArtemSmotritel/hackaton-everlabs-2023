package com.hackaton.makemate.web.dto.event;

import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.web.dto.interest.InterestMapper;
import com.hackaton.makemate.web.dto.user.UserMapper;
import org.springframework.stereotype.Component;

@Component

public class eventDtoMapper {
    private final InterestMapper interestMapper;
    private final UserMapper userMapper;
    public eventDtoMapper(InterestMapper interestMapper, UserMapper userMapper) {
        this.interestMapper = interestMapper;
        this.userMapper = userMapper;
    }

    public eventDto toDto(Event eventEntity)
    {
        if (eventEntity == null) return null;
        eventDto tempDto = new eventDto(
                eventEntity.getId(),
                eventEntity.getName(),
        eventEntity.getDateOfEvent(),

        0L,

         0L);
        return tempDto;
    }
}
