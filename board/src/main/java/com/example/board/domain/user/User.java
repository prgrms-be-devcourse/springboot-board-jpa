package com.example.board.domain.user;

import static java.util.Objects.nonNull;

import com.example.board.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");
  private static final int NAME_MAX = 30;
  private static final int AGE_MIN = 1;
  private static final int HOBBY_MAX = 100;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = NAME_MAX)
  @Column(nullable = false, length = NAME_MAX)
  private String name;

  @NotNull
  @Size(min = AGE_MIN)
  @Column(nullable = false)
  private int age;

  @NotBlank
  @Size(max = HOBBY_MAX)
  @Column(nullable = false, length = HOBBY_MAX)
  private String hobby;

  public void updateName(String name) {
    if (nonNull(name)) {
      validateName(name);
      this.name = name;
    }
  }

  public void updateHobby(String hobby) {
    if (nonNull(hobby)) {
      this.hobby = hobby;
    }
  }

  public void validateName(String name) {
    if (isValidName(name)) {
      throw new IllegalArgumentException("이름은 한글과 영어만 가능합니다.");
    }
  }

  public boolean isValidName(String name) {
    boolean valid = NAME_PATTERN.matcher(name).matches();

    return !valid;
  }

  public User(String name, int age, String hobby) {
    validateName(name);
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }
}
