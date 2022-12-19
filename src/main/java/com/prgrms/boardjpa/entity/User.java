package com.prgrms.boardjpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

  @Id
  @Column(name = "user_id", nullable = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "age", nullable = false, length = 3)
  private int age;

  @Column(name = "hobby", nullable = true)
  private String hobby;

  @Builder.Default
  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

  public void addPost(Post post) {
    post.changeUser(this);
  }
}
