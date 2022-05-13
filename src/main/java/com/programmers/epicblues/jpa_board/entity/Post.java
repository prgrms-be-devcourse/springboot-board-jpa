package com.programmers.epicblues.jpa_board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends CommonFieldEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String content;
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Builder
  public Post(String title, String content, String createdBy) {
    this.title = title;
    this.content = content;
    this.createdBy = createdBy;
  }

  public void changeContent(String newContent) {
    this.content = newContent;
  }

  public void removeUser() {
    if (this.user != null) {
      var removeTarget = this.user;
      this.user = null;
      removeTarget.removePostById(id);
    }
  }

  public void assignUser(User user) {
    this.user = user;
    if (!user.contains(this)) {
      user.addPost(this);
    }
  }
}
