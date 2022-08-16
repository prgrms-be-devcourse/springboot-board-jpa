package com.devcourse.springjpaboard.application.post.model;

import com.devcourse.springjpaboard.application.user.model.User;
import com.devcourse.springjpaboard.core.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "post")
@Getter
public class Post extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  protected Post() {
  }

  public Post(String title, String content,
      User user) {
    this.title = title;
    this.content = content;
    this.user = user;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setUser(User user) {
    if (this.user != null) {
      this.user.getPosts().remove(this);
    }
    this.user = user;
    user.getPosts().add(this);
  }
}
