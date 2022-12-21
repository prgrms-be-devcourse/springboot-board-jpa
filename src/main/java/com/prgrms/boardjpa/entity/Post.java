package com.prgrms.boardjpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "posts")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

  @Id
  @Column(name = "post_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = true)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  public void changeUser(User user) {
    if (Objects.nonNull(this.user)) {
      user.getPosts().remove(this);
    }
    this.user = user;
    user.getPosts().add(this);
  }

  public void changeTitle(String title) {
    this.title = title;
  }

  public void changeContent(String content) {
    this.content = content;
  }
}
