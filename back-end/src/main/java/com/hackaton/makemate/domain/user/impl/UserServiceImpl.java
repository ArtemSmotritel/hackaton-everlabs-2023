package com.hackaton.makemate.domain.user.impl;

import com.hackaton.makemate.database.user.UserRepository;
import com.hackaton.makemate.domain.exception.BadRequestException;
import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.domain.user.UserService;
import com.hackaton.makemate.domain.user.model.UserMatchRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> matchingUsers(Long userId) {
    User user = fetchUserById(userId);

    List<User> users = userRepository.findAll();

    // TODO: write actual algorithm
    return matchAlgorithm(user, users);
  }

  @Override
  public User matchWithUser(Long userId, UserMatchRequest request) {
    if (!Objects.equals(userId, request.userId()))
      throw new BadRequestException("You can't match another users :0");

    User user = fetchUserById(request.userId());
    User match = fetchUserById(request.matchId());

    user.getMatches().add(match);

    userRepository.save(user);

    return match;
  }

  @Override
  public User getUserById(Long userId) {
    return fetchUserById(userId);
  }

  private User fetchUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () -> new BadRequestException(String.format("User with id %s doesn't exist", userId)));
  }

  private List<User> matchAlgorithm(User sender, List<User> targets) {
    return new ArrayList<>(targets);
  }
}
