package com.sdardew.board.domain.user;

import com.sdardew.board.domain.post.Post;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;
  private String name;
  private Integer age;
  private String hobby;
  private LocalDateTime createAt;
  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

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

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setHobby(String hobby) {
    this.hobby = hobby;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public void addPost(Post post) {
    posts.add(post);
  }
}
