package dev.jpaboard.user.domain;

import dev.jpaboard.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(length = 5, nullable = false)
  private String name;

  @Column(length = 3, nullable = false)
  private int age;

  @Column(length = 20)
  private String hobby;

  @Builder
  private User(String name, int age, String hobby) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }

}
