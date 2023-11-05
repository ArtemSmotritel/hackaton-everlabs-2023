package com.hackaton.makemate.domain.event.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hackaton.makemate.domain.event.EventService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class EventScheduler {
  private final ScheduledExecutorService executorService;
  private final EventService eventService;

  public EventScheduler(EventService eventService) {
    this.eventService = eventService;
    this.executorService =
        Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setNameFormat("EventSchedulerThread-%s").build());

    this.executorService.scheduleAtFixedRate(() -> {}, 100, 1000, TimeUnit.MICROSECONDS);
  }

  private void createPrivateEvents(int count) {}
}
