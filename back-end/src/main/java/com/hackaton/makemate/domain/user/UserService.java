package com.hackaton.makemate.domain.user;

import com.hackaton.makemate.domain.user.model.UserMatchRequest;
import java.util.List;

public interface UserService {
  List<User> matchingUsers(Long userId);

  /** Returned matched user */
  User matchWithUser(Long userId, UserMatchRequest request);

  User getUserById(Long userId);
}
