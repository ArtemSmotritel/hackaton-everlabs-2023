package com.hackaton.makemate.domain.interest;

import com.google.common.base.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "interest")
public class Interest {
  @Id
  @SequenceGenerator(
      name = "interest_sequence",
      sequenceName = "interest_sequence",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interest_sequence")
  @Column(name = "id", updatable = false)
  private Long id;

  private String name;

  public Interest() {}

  public Interest(Long id, String name) {
    this.id = id;
    this.name = name;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Interest interest = (Interest) o;
    return Objects.equal(id, interest.id) && Objects.equal(name, interest.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, name);
  }

  @Override
  public String toString() {
    return "Interest{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
