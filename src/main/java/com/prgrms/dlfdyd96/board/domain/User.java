package com.prgrms.dlfdyd96.board.domain;

import com.prgrms.dlfdyd96.board.user.dto.UpdateUserRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @Column(name = "age", nullable = false)
  private int age;

  @Column(name = "hobby", length = 50)
  private String hobby; // TODO: 1급 컬렉션

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  public void addPost(Post post) {
    post.setUser(this);
  }

  public void update(UpdateUserRequest userDto) {
    this.name = userDto.getName();
    this.age = userDto.getAge();
    this.hobby = userDto.getHobby();
  }
}
