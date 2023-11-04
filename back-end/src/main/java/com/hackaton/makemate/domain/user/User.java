package com.hackaton.makemate.domain.user;

import com.google.common.base.Objects;
import com.hackaton.makemate.domain.interest.Interest;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "firstname",
            nullable = false,
            length = 64
    )
    private String firstName;
    @Column(
            name = "lastname",
            nullable = false,
            length = 64
    )
    private String lastName;


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Interest> interests;

    private String description;
    private LocalDateTime birthDate;
    private String avatarUrl;

    public User() {
    }

    public User(Long id, String firstName, String lastName, List<Interest> interests, LocalDateTime birthDate, String avatarUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.interests = interests;
        this.birthDate = birthDate;
        this.avatarUrl = avatarUrl;
    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String firstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String lastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Interest> interests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime birthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String avatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", interests=" + interests +
                ", description='" + description + '\'' +
                ", birthDate=" + birthDate +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(id, user.id) && Objects.equal(firstName, user.firstName) && Objects.equal(lastName, user.lastName) && Objects.equal(interests, user.interests) && Objects.equal(description, user.description) && Objects.equal(birthDate, user.birthDate) && Objects.equal(avatarUrl, user.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firstName, lastName, interests, description, birthDate, avatarUrl);
    }
}
