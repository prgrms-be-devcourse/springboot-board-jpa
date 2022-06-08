package com.devcourse.springjpaboard.application.post.model;

import com.devcourse.springjpaboard.application.user.model.User;
import com.devcourse.springjpaboard.core.model.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
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

  public void setUser(User user) {
    if (Objects.nonNull(this.user)) {
      this.user.getPosts().remove(this);
    }
    this.user = user;
    user.getPosts().add(this);
  }
}
