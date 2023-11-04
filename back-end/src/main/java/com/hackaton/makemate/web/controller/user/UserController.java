package com.hackaton.makemate.web.controller.user;

import com.google.common.net.HttpHeaders;
import com.hackaton.makemate.domain.user.UserService;
import com.hackaton.makemate.web.dto.user.UserMapper;
import com.hackaton.makemate.web.dto.user.UserMatchRequestDto;
import com.hackaton.makemate.web.dto.user.UserPreviewDto;
import java.util.List;
import org.springframework.web.bind.annotation.*;

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
  public List<UserPreviewDto> previewUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) Long id) {
    return userMapper.toDto(userService.matchingUsers(id));
  }

  @GetMapping("/{id}")
  public UserPreviewDto getUserById(@PathVariable("id") Long id) {
    return userMapper.toDto(userService.getUserById(id));
  }
}
