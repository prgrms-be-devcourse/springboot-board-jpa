package com.eden6187.jpaboard.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "post")
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Lob
  @Column(name = "content")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  // Post 가 삭제 된다고 해도 게시물은 남아 있어야 하기 때문에 CascadeType.PERSIST
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @Builder
  public Post(String createdBy, LocalDateTime createdAt, Long id, String title,
      String content, User user) {
    super(createdBy, createdAt);
    this.id = id;
    this.title = title;
    this.content = content;
    this.user = user;
  }

  // 연관 관계 편의 메소드
  public void setUser(User user) {
    if (Objects.nonNull(this.user)) {
      this.user.getPosts().remove(this);
    }

    this.user = user;
    user.getPosts().add(this);

    this.createdBy = user.getName();
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateContent(String content) {
    this.content = content;
  }

  public boolean isBelongedTo(User user) {
    if (!Objects.nonNull(user)) {
      return false;
    }

    return this.user.getId().equals(user.getId());
  }
}
