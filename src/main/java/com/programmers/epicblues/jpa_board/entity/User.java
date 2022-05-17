package com.programmers.epicblues.jpa_board.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private int age;
  private String hobby;
  @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  @Builder
  public User(String name, int age, String hobby, String createdBy) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
    this.createdBy = createdBy;
  }

  public void addPost(Post post) {
    posts.add(post);
    post.assignUser(this);
  }

  public void removePostById(Long id) {
    Post post = posts.stream().filter(candidatePost -> candidatePost.getId().equals(id)).findFirst()
        .orElseThrow();
    post.removeUser();
    this.posts.remove(post);
  }

  public boolean contains(Post post) {
    return posts.contains(post);
  }

  public void addPosts(List<Post> posts) {
    posts.forEach(this::addPost);
  }
}
