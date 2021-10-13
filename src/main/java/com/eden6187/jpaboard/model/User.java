package com.eden6187.jpaboard.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 사용을 방지
@Slf4j
public class User extends BaseEntity {

  @Builder
  public User(String createdBy, LocalDateTime createdAt, Long id, String name,
      Integer age, String hobby) {
    super(createdBy, createdAt);
    this.id = id;
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 전략
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "age", nullable = false)
  private Integer age;

  @Column(name = "hobby", length = 30)
  private String hobby;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private final List<Post> posts = new ArrayList<>();

  // 연관 관계 편의 메소드
  public void addPost(Post post) {
    post.setUser(this);
  }

}
