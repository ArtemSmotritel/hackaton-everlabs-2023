package com.hackaton.makemate.domain.interest;

import java.util.List;

public interface InterestService {
  List<Interest> getAllInterests();

  Interest createInterest(String name);
}
