package com.sdardew.board.domain.post;

import com.sdardew.board.domain.post.converter.UserIdConverter;
import com.sdardew.board.domain.user.User;
import com.sdardew.board.dto.post.PostDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NotBlank
  private Long id;

  @Column(name="title")
  @NotBlank
  @Size(min = 3, max = 255)
  private String title;

  @Column(name="content")
  @NotBlank
  @Size(min = 3, max = 1024)
  private String content;

  @Column(name="create_at")
  @NotNull
  private LocalDateTime createAt;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @Convert(converter = UserIdConverter.class)
  private User user; // user 테이블과 연관

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

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
