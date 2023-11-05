package com.hackaton.makemate.web.controller.match;

import com.google.common.net.HttpHeaders;
import com.hackaton.makemate.domain.user.UserService;
import com.hackaton.makemate.web.dto.user.UserMapper;
import com.hackaton.makemate.web.dto.user.UserMatchRequestDto;
import com.hackaton.makemate.web.dto.user.UserPreviewDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/matches")
@RestController
public class MatchController {
  private final UserService userService;
  private final UserMapper userMapper;

  public MatchController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PostMapping()
  public UserPreviewDto matchWithUser(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Long id,
      @RequestBody UserMatchRequestDto requestDto) {
    return userMapper.toDto(userService.matchWithUser(id, userMapper.toEntity(requestDto)));
  }
}
