package com.hackaton.makemate.domain.user;

import com.google.common.base.Objects;
import com.hackaton.makemate.domain.event.Event;
import com.hackaton.makemate.domain.interest.Interest;
import jakarta.persistence.*;
import java.util.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  @ManyToMany(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "users_interests",
      joinColumns = @JoinColumn(name = "user_id"),
      foreignKey = @ForeignKey(name = "user_interest_fk"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Interest> interests = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "users_matches",
      joinColumns = @JoinColumn(name = "user_id"),
      foreignKey = @ForeignKey(name = "user_interest_fk"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<User> matches = new HashSet<>();

  private String description;
  private Date birthDate;
  private String avatarUrl;

  @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Event> createdEvents = new ArrayList<>();

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

  public Set<User> getMatches() {
    return matches;
  }

  public Set<User> getAllUsersView() {
    Set<User> temp = new HashSet<>(getMatches());
    temp.add(this);
    return temp;
  }

  public void setMatches(Set<User> matches) {
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

  public int getAge() {
    if (getBirthDate() == null) return -1;

    Calendar today = Calendar.getInstance();

    Calendar birthDate = Calendar.getInstance();
    birthDate.setTime(getBirthDate());

    int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

    if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
      age--;
    }

    return age;
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
