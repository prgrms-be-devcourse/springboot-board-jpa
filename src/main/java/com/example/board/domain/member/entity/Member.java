package com.example.board.domain.member.entity;

import com.example.board.domain.common.entity.BaseTimeEntity;
import com.example.board.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "age") // min 1
  private int age;

  @Column(name = "hobby")
  private String hobby;

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  private List<Post> posts = new ArrayList<>();

  public Member(String name, int age, String hobby) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }

  public void changeName(String newName) {
    this.name = newName;
  }

  public void changeAge(int newAge) {
    this.age = newAge;
  }

  public void changeHobby(String newHobby) {
    this.hobby = newHobby;
  }
}
