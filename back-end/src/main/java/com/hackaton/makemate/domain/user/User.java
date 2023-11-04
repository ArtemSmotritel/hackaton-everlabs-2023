package com.hackaton.makemate.domain.user;

import com.google.common.base.Objects;
import com.hackaton.makemate.domain.interest.Interest;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
  @Id
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "firstname", nullable = false, length = 64)
  private String firstName;

  @Column(name = "lastname", nullable = false, length = 64)
  private String lastName;

  @ManyToMany()
  @CollectionTable(
      name = "users_interests",
      joinColumns = @JoinColumn(name = "user_id"),
      foreignKey = @ForeignKey(name = "user_interest_fk"))
  private List<Interest> interests = new ArrayList<>();

  @ManyToMany()
  @CollectionTable(
      name = "users_matches",
      joinColumns = @JoinColumn(name = "user_id"),
      foreignKey = @ForeignKey(name = "user_interest_fk"))
  private List<User> matches = new ArrayList<>();

  private String description;
  private Date birthDate;
  private String avatarUrl;

  public User() {}

  public User(
      Long id,
      String firstName,
      String lastName,
      Date birthDate,
      String description,
      String avatarUrl) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.description = description;
    this.avatarUrl = avatarUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public List<User> getMatches() {
    return matches;
  }

  public void setMatches(List<User> matches) {
    this.matches = matches;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Interest> getInterests() {
    return interests;
  }

  public void setInterests(List<Interest> interests) {
    this.interests = interests;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", interests="
        + interests
        + ", description='"
        + description
        + '\''
        + ", birthDate="
        + birthDate
        + ", avatarUrl='"
        + avatarUrl
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equal(id, user.id)
        && Objects.equal(firstName, user.firstName)
        && Objects.equal(lastName, user.lastName)
        && Objects.equal(interests, user.interests)
        && Objects.equal(description, user.description)
        && Objects.equal(birthDate, user.birthDate)
        && Objects.equal(avatarUrl, user.avatarUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, firstName, lastName, interests, description, birthDate, avatarUrl);
  }
}
