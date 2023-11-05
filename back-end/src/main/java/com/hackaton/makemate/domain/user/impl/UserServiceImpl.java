package com.hackaton.makemate.domain.user.impl;

import com.hackaton.makemate.database.interest.InterestRepository;
import com.hackaton.makemate.database.user.UserRepository;
import com.hackaton.makemate.domain.exception.BadRequestException;
import com.hackaton.makemate.domain.interest.Interest;
import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.domain.user.UserMatcher;
import com.hackaton.makemate.domain.user.UserService;
import com.hackaton.makemate.domain.user.model.UserMatchRequest;
import com.hackaton.makemate.domain.user.model.UserUpdateRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final InterestRepository interestRepository;
  private final UserMatcher userMatcher;

  public UserServiceImpl(
      UserRepository userRepository,
      UserMatcher userMatcher,
      InterestRepository interestRepository) {
    this.interestRepository = interestRepository;
    this.userRepository = userRepository;
    this.userMatcher = userMatcher;
  }

  @Override
  public List<User> matchingUsers(Long userId) {
    User user = fetchUserById(userId);

    List<User> users = userRepository.findAll();

    return userMatcher.matchAlgorithm(user, users);
  }

  @Override
  public User matchWithUser(Long userId, UserMatchRequest request) {
    if (!Objects.equals(userId, request.userId()))
      throw new BadRequestException("You can't match another users :0");

    if (Objects.equals(request.userId(), request.matchId()))
      throw new BadRequestException("You can't match with yourself");

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

  @Override
  public User updateUserById(Long userId, UserUpdateRequest request) {
    User user = fetchUserById(userId);

    List<Interest> interests =
        new ArrayList<>(request.interests().stream().map(this::fetchInterestById).toList());

    String firstName = user.getFirstName();
    String lastName = user.getLastName();

    if (!Strings.isBlank(request.firstName())) {
      firstName = request.firstName();
    }

    if (!Strings.isBlank(request.lastName())) {
      lastName = request.lastName();
    }

    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setInterests(interests);

    userRepository.save(user);

    return user;
  }

  private Interest fetchInterestById(Long interestId) {
    return interestRepository
        .findById(interestId)
        .orElseThrow(
            () ->
                new BadRequestException(
                    String.format("Interest with id %s doesn't exist", interestId)));
  }

  private User fetchUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () -> new BadRequestException(String.format("User with id %s doesn't exist", userId)));
  }
}
