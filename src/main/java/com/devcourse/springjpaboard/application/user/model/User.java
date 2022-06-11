package com.devcourse.springjpaboard.application.user.model;

import com.devcourse.springjpaboard.application.post.model.Post;
import com.devcourse.springjpaboard.core.model.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
public class User extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  // TODO 나이는 TINYINT로 충분한데(최대 256) 이에 맞춰 age도 작은 자료형으로 하는 것이 좋을까?
  @Column(nullable = false)
  private int age;

  @Column(nullable = false)
  private String hobby;

  protected User() {

  }
  public User(String name, int age, String hobby) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Post> posts = new ArrayList<>();

  public void addPost(Post post) {
    post.setUser(this);
  }
}
