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

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    final int userCount = 80;

    List<Interest> interests = interestRepository.findAll();

    for (int i = 0; i < 80; i++) {
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
      // Don't ask what is it IDK FO
      if (((userCount | 31) & i) == 0 && (i & (i - 1)) == 0) {
        Collections.shuffle(interests);
      }
    }
  }

  @Transactional
  public void performInterestMigration() {
    // Yes I ENJOY SHIT CODING
    interestRepository.save(new Interest(null, "Sport"));
    interestRepository.save(new Interest(null, "Music"));
    interestRepository.save(new Interest(null, "Movie"));
    interestRepository.save(new Interest(null, "Dancing"));
    interestRepository.save(new Interest(null, "Cooking"));
    interestRepository.save(new Interest(null, "Reading"));
    interestRepository.save(new Interest(null, "Swimming"));
    interestRepository.save(new Interest(null, "Gaming"));
    interestRepository.save(new Interest(null, "Traveling"));
    interestRepository.save(new Interest(null, "Hiking"));
    interestRepository.save(new Interest(null, "Photography"));
    interestRepository.save(new Interest(null, "Painting"));
    interestRepository.save(new Interest(null, "Writing"));
    interestRepository.save(new Interest(null, "Gardening"));
    interestRepository.save(new Interest(null, "Knitting"));
    interestRepository.save(new Interest(null, "Cycling"));
    interestRepository.save(new Interest(null, "Yoga"));
    interestRepository.save(new Interest(null, "Meditation"));
    interestRepository.save(new Interest(null, "Fishing"));
    interestRepository.save(new Interest(null, "DIY"));
    interestRepository.save(new Interest(null, "Surfing"));
    interestRepository.save(new Interest(null, "Skateboarding"));
    interestRepository.save(new Interest(null, "Blogging"));
    interestRepository.save(new Interest(null, "Volunteering"));
    interestRepository.save(new Interest(null, "Coding"));
    interestRepository.save(new Interest(null, "Board Games"));
    interestRepository.save(new Interest(null, "Astronomy"));
    interestRepository.save(new Interest(null, "Magic and Illusion"));
    interestRepository.save(new Interest(null, "Stand-up Comedy"));
    interestRepository.save(new Interest(null, "Podcasting"));
    interestRepository.save(new Interest(null, "Language Learning"));
    interestRepository.save(new Interest(null, "Home Brewing"));
    interestRepository.save(new Interest(null, "Aquarium Keeping"));

  }

  private List<Interest> randomSubArray(List<Interest> interests) {
    if (interests.isEmpty()) {
      return Lists.newArrayList();
    }

    int size = new Random().nextInt(1, Math.max(8, interests.size()));
    return new ArrayList<>(interests.subList(0, size));
  }
}
