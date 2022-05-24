package com.sdardew.board.domain.post;

import com.sdardew.board.domain.post.converter.UserIdConverter;
import com.sdardew.board.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name="title")
  private String title;

  @Column(name="content")
  private String content;

  @Column(name="create_at")
  private LocalDateTime createAt;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @Convert(converter = UserIdConverter.class)
  private User user; // user 테이블과 연관

  public String getContent() {
    return content;
  }

  @Convert(converter = UserIdConverter.class)
  public User getUser() {
    return user;
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
    user.addPost(this);
  }

  public PostDto toPostDto() {
    return new PostDto(id, title, content, createAt, user.getId());
  }

  @Override
  public String toString() {
    return "Post{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", content='" + content + '\'' +
      ", createAt=" + createAt +
      ", user=" + user.getId() +
      '}';
  }
}
