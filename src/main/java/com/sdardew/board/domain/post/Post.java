package com.sdardew.board.domain.post;

import com.sdardew.board.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createAt;
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user; // user 테이블과 연관

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public User getUser() {
    return user;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public void setUser(User user) {
    if(Objects.nonNull(this.user)) {
      this.user.getPosts().remove(this);
    }
    this.user = user;
    user.getPosts().add(this);
  }
}
