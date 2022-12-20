package com.sdardew.board.domain.user;

import com.sdardew.board.domain.post.Post;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Size(min = 3, max = 20)
  private String name;

  @Max(value = 120)
  @Min(12)
  private Integer age;

  @NotBlank
  private String hobby;

  @NotNull
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

  public User() {
  }

  public User(String name, Integer age, String hobby, LocalDateTime createdAt) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
    this.createdAt = createdAt;
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

  public List<Post> getPosts() {
    return posts;
  }

  public void addPost(Post post) {
    posts.add(post);
  }
}
