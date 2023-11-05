package com.hackaton.makemate.domain.interest.impl;

import com.hackaton.makemate.database.interest.InterestRepository;
import com.hackaton.makemate.domain.interest.Interest;
import com.hackaton.makemate.domain.interest.InterestService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InterestServiceImplementation implements InterestService {
  private final InterestRepository interestRepository;

  public InterestServiceImplementation(InterestRepository interestRepository) {
    this.interestRepository = interestRepository;
  }

  @Override
  public List<Interest> getAllInterests() {
    return interestRepository.findAll();
  }

  @Override
  public Interest createInterest(String name) {
    return interestRepository.save(new Interest(null, name));
  }
}
