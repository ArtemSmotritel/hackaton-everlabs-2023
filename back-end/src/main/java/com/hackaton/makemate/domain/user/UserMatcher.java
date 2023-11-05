package com.hackaton.makemate.domain.user;

import com.hackaton.makemate.domain.interest.Interest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMatcher {

  // Ration between importance of Interests:Age in calculation
  private static final int INTERESTS_WEIGHT = 10;

  private static final double AGE_WEIGHT = 1.0;

  public List<User> matchAlgorithm(User sender, List<User> targets) {
    if (sender == null || targets == null) return null;

    List<User> filteredTargets =
        targets.stream()
            .filter(
                user ->
                    !Objects.equals(sender.getId(), user.getId())
                        && !sender.getMatches().contains(user))
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
