package com.hackaton.makemate.domain.event.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.domain.event.EventService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventScheduler {
  private static final Logger logger = LoggerFactory.getLogger(EventScheduler.class);
  private final ScheduledExecutorService executorService;
  private final EventService eventService;

  public EventScheduler(EventService eventService) {
    this.eventService = eventService;
    this.executorService =
        Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setNameFormat("EventSchedulerThread-%s").build());
  }

  public void init() {
    this.executorService.scheduleAtFixedRate(
        () -> {
          try {
            List<Event> pairedEvents = eventService.createPairedEvents();
            logger.info("Order created {}: \n{}", LocalDateTime.now(), pairedEvents);
          } catch (Exception e) {
            logger.warn("Exception {} thrown while triyng to create pairs", e.getMessage());
          }
        },
        15_000,
        10_000,
        TimeUnit.MILLISECONDS);
  }
}
