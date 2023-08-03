package com.example.board.domain.post;

import static java.util.Objects.nonNull;

import com.example.board.domain.user.User;
import com.example.board.global.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

  private static final int TITLE_MAX = 30;
  private static final int CONTENT_MAX = 100;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = TITLE_MAX)
  @Column(nullable = false, length = TITLE_MAX)
  private String title;

  @NotBlank
  @Size(max = CONTENT_MAX)
  @Column(nullable = false, length = CONTENT_MAX)
  private String content;

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private User user;

  public void updateTitle(String title) {
    if (nonNull(title) && !title.isBlank()) {
      this.title = title;
    }
  }

  public void updateContent(String content) {
    if (nonNull(content) && !content.isBlank()) {
      this.content = content;
    }
  }

  public Post(String title, String content, User user) {
    if (nonNull(title) && nonNull(content) && nonNull(user)) {
      this.title = title;
      this.content = content;
      this.user = user;
      this.setCreatedBy(user.getName());
    }
  }
}