package com.hackaton.makemate.web.controller.user;

import com.google.common.net.HttpHeaders;
import com.hackaton.makemate.domain.user.UserService;
import com.hackaton.makemate.web.dto.user.UserMapper;
import com.hackaton.makemate.web.dto.user.UserPreviewDto;
import com.hackaton.makemate.web.dto.user.UserUpdateRequestDto;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController()
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @GetMapping()
  public List<UserPreviewDto> previewUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) Long id) {
    return userMapper.toDto(userService.matchingUsers(id));
  }

  @GetMapping("/{id}")
  public UserPreviewDto getUserById(@PathVariable("id") Long id) {
    return userMapper.toDto(userService.getUserById(id));
  }

  @PatchMapping("/{id}")
  public UserPreviewDto updateCurrentUser(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
      // IGNORE THAT SHIT, THIS ONE FOR BROKE BITCHES
      @PathVariable("id") Long __,
      @RequestBody UserUpdateRequestDto requestDto) {
    return userMapper.toDto(userService.updateUserById(userId, userMapper.toEntity(requestDto)));
  }
}
