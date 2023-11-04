package com.hackaton.makemate;

import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import com.hackaton.makemate.database.event.EventRepository;
import com.hackaton.makemate.database.interest.InterestRepository;
import com.hackaton.makemate.database.user.UserRepository;
import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.domain.interest.Interest;
import com.hackaton.makemate.domain.user.User;
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
  @Autowired private EventRepository eventRepository;

  public static void main(String[] args) {
    SpringApplication.run(MakemateApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return (__) -> {
      // TODO: create real migration with flyway if possible
      performInterestMigration();
      performUserMigration();
      userRepository.flush();
      performEventMigration();
    };
  }

  @Transactional
  public void performEventMigration() {
    Faker faker = new Faker();

    List<Event> interests = eventRepository.findAll();
    Collections.shuffle(interests);

    for (int i = 0; i < 20; i++) {
      Event event =
          new Event(
              null,
              faker.name().firstName(),
              faker.name().lastName(),
              faker.date().birthday(),
              false);

      eventRepository.save(event);
    }
  }

  @Transactional
  public void performUserMigration() {
    Faker faker = new Faker();

    List<Interest> interests = interestRepository.findAll();
    Collections.shuffle(interests);

    for (int i = 0; i < 20; i++) {
      final String description =
          String.format(
              "In the age-old lands of Middle-earth, %s is found wandering the paths of %s. "
                  + "Here, the tales of old come to life and legends walk amongst the living.",
              faker.lordOfTheRings().character(), faker.lordOfTheRings().location());

      User user =
          new User(
              null,
              faker.name().firstName(),
              faker.name().lastName(),
              faker.date().birthday(),
              description,
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
    if (interests.isEmpty()) {
      return Lists.newArrayList();
    }

    int size = new Random().nextInt(1, interests.size());
    return new ArrayList<>(interests.subList(0, size));
  }
}
