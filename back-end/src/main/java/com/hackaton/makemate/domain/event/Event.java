package com.hackaton.makemate.domain.event;

import com.google.common.base.Objects;
import com.hackaton.makemate.domain.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {
  @Id
  @Column(name = "id", updatable = false)
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private EvenType type;

  private String name;
  private String description;
  private LocalDateTime dateOfEvent;

  @ManyToOne private User createdBy;

  @ManyToMany private Set<User> participants = new HashSet<>();

  private Boolean active;

  private String place;

  public Event(
      Long id,
      String name,
      String description,
      LocalDateTime dateOfEvent,
      User createdBy,
      boolean active,
      EvenType type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.dateOfEvent = dateOfEvent;
    this.createdBy = createdBy;
    this.active = active;
    this.type = type;
  }

  public Event() {}

  public Set<User> getAllUsers() {
    Set<User> part = new HashSet<>(participants);
    part.add(createdBy);
    return part;
  }

  public String getDateString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM h a");

    return dateOfEvent.format(formatter);
  }

  public EvenType getType() {
    return type;
  }

  public void setType(EvenType eventType) {
    this.type = eventType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDateOfEvent() {
    return dateOfEvent;
  }

  public void setDateOfEvent(LocalDateTime dateOfEvent) {
    this.dateOfEvent = dateOfEvent;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public Set<User> getParticipants() {
    return participants;
  }

  public void setParticipants(Set<User> participants) {
    this.participants = participants;
  }

  public Integer getCurrentMatchCount() {
    if (participants == null || participants.isEmpty()) return 0;

    Set<User> temp = new HashSet<>(participants);
    temp.retainAll(createdBy.getMatches());

    return temp.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Objects.equal(id, event.id)
        && type == event.type
        && Objects.equal(name, event.name)
        && Objects.equal(description, event.description)
        && Objects.equal(dateOfEvent, event.dateOfEvent)
        && Objects.equal(createdBy, event.createdBy)
        && Objects.equal(participants, event.participants)
        && Objects.equal(active, event.active)
        && Objects.equal(place, event.place);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        id, type, name, description, dateOfEvent, createdBy, participants, active, place);
  }
}
