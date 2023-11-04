package com.hackaton.makemate.domain.user.impl;

import com.hackaton.makemate.database.user.UserRepository;
import com.hackaton.makemate.domain.exception.BadRequestException;
import com.hackaton.makemate.domain.interest.Interest;
import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.domain.user.UserService;
import com.hackaton.makemate.domain.user.model.UserMatchRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  // This is the weight for how much interests matter compared to age.
  // For example, if set to 10, interests are 10 times more important than age difference.
  private static final int INTERESTS_WEIGHT = 10;

  // Age weight could be lesser as interests are given higher priority.
  private static final double AGE_WEIGHT = 1.0;

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

  private User fetchUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () -> new BadRequestException(String.format("User with id %s doesn't exist", userId)));
  }

  private List<User> matchAlgorithm(User sender, List<User> targets) {
    if (sender == null || targets == null) return null;

    List<User> filteredTargets =
        targets.stream()
            .filter(
                user ->
                    !Objects.equals(sender.getId(), user.getId())
                        && !sender.getMatches().contains(targets))
            .toList();

    Map<User, Double> userToScore = new HashMap<>();

    for (User target : filteredTargets) {
      double score = calculateMatchScore(sender, target);
      userToScore.put(target, score);
    }

    return userToScore.entrySet().stream()
        .sorted(Map.Entry.<User, Double>comparingByValue().reversed())
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }

  private double calculateMatchScore(User sender, User target) {
    double score = 0.0;

    List<Interest> commonInterests =
        sender.getInterests().stream().filter(target.getInterests()::contains).toList();
    score += commonInterests.size() * INTERESTS_WEIGHT;

    int ageDifference = Math.abs(sender.getAge() - target.getAge());
    double ageScore = ageDifference == 0 ? 1.0 : 1.0 / (ageDifference * AGE_WEIGHT);
    score += ageScore;

    return score;
  }
}
