package com.sdardew.board.dto.user;

public class UserDto {
  private Long id;
  private String name;
  private Integer age;
  private String hobby;

  public UserDto(Long id, String name, Integer age, String hobby) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public String getHobby() {
    return hobby;
  }
}
