package com.hackaton.makemate.database.event;

import com.hackaton.makemate.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {}
