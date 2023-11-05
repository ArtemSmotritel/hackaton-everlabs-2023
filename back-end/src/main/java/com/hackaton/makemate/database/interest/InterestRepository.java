package com.hackaton.makemate.database.interest;

import com.hackaton.makemate.domain.interest.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {}
