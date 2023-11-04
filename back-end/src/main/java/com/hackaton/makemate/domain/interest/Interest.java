package com.hackaton.makemate.domain.interest;

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

  public Long id() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String name() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
