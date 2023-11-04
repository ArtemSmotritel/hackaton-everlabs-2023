package com.hackaton.makemate.web.controller.user;

import com.google.common.net.HttpHeaders;
import com.hackaton.makemate.database.user.UserService;
import com.hackaton.makemate.web.dto.user.UserMapper;
import com.hackaton.makemate.web.dto.user.UserPreviewDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserPreviewDto> getPreviewUsers(
            @RequestHeader(HttpHeaders.AUTHORIZATION) Long id
    ) {
        return userMapper
                .toDto(userService.matchingUsers(id));
    }
}
