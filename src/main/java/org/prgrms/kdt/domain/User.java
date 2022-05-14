package org.prgrms.kdt.domain;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Size(min = 1, max = 20)
  @Column(name = "name", length = 20, nullable = false)
  private String name;

  @NotNull
  @Positive
  @Column(name = "age", nullable = false)
  private int age;

  @Size(min = 1, max = 20)
  @Column(name = "hobby", length = 20, nullable = false)
  private String hobby;

  @OneToMany(mappedBy = "user")
  private final List<Post> posts = new ArrayList<>();

  @Builder
  public User(String name, int age, String hobby) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }
}