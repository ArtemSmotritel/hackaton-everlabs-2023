package com.hackaton.makemate;

import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import com.hackaton.makemate.domain.interest.Interest;
import com.hackaton.makemate.domain.interest.InterestRepository;
import com.hackaton.makemate.domain.user.User;
import com.hackaton.makemate.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MakemateApplication {

  @Autowired private UserRepository userRepository;

  @Autowired private InterestRepository interestRepository;

  public static void main(String[] args) {
    SpringApplication.run(MakemateApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return (__) -> {
      // TODO: create real migration with flyway
      performInterestMigration();
      performUserMigration();
    };
  }

  @Transactional
  public void performUserMigration() {
    Faker faker = new Faker();

    List<Interest> interests = interestRepository.findAll();
    Collections.shuffle(interests);

    for (int i = 0; i < 20; i++) {
      User user =
          new User(
              null,
              faker.name().firstName(),
              faker.name().lastName(),
              faker.date().birthday(),
              null);
      user.setInterests(randomSubArray(interests));

      userRepository.save(user);
    }
  }

  @Transactional
  public void performInterestMigration() {
    interestRepository.save(new Interest(null, "Sport"));
    interestRepository.save(new Interest(null, "Music"));
    interestRepository.save(new Interest(null, "Movie"));
    interestRepository.save(new Interest(null, "Dancing"));
    interestRepository.save(new Interest(null, "Cooking"));
    interestRepository.save(new Interest(null, "Reading"));
    interestRepository.save(new Interest(null, "Swimming"));
  }

  private List<Interest> randomSubArray(List<Interest> interests) {
    int size = new Random().nextInt(0, interests.size());
    return new ArrayList<>(interests.subList(0, size));
  }
}
