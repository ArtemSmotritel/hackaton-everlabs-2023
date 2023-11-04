package com.hackaton.makemate.domain.event;

import com.google.common.base.Objects;
import com.hackaton.makemate.domain.user.User;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "event")
public class Event {
  @Id
  @Column(name = "id", updatable = false)
  @GeneratedValue
  private Long id;

  public Type getEventType() {
    return eventType;
  }

  public void setEventType(Type eventType) {
    this.eventType = eventType;
  }

  @Enumerated(EnumType.STRING)
  private Type eventType;

  private String name;
  private String description;

  private Date dateOfEvent;

  @ManyToOne
  @JoinColumn(name = "create_by") // Define the foreign key column
  private User createdBy;

  private Boolean isActive;
  private String Place;

  public Event(Long id, String name, String description, Date dateOfEvent, boolean isActive) {
    this.name = name;
    this.description = description;
    this.dateOfEvent = dateOfEvent;
    this.createdBy = null;
    this.isActive = isActive;
    Random random = new Random();

    int randomIndex = random.nextInt(2);
    this.eventType = randomIndex == 0 ? Type.PUBLIC : Type.PRIVATE;
  }

  public Event() {}

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

  public Date getDateOfEvent() {
    return dateOfEvent;
  }

  public void setDateOfEvent(Date dateOfEvent) {
    this.dateOfEvent = dateOfEvent;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public String getPlace() {
    return Place;
  }

  public void setPlace(String place) {
    Place = place;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Objects.equal(id, event.id)
        && Objects.equal(name, event.name)
        && Objects.equal(description, event.description)
        && Objects.equal(dateOfEvent, event.dateOfEvent)
        && Objects.equal(createdBy, event.createdBy)
        && Objects.equal(isActive, event.isActive)
        && Objects.equal(Place, event.Place);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, name, description, dateOfEvent, createdBy, isActive, Place);
  }
}
