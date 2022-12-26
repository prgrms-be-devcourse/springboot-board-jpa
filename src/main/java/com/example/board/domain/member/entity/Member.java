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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "NAME_UNIQUE", columnNames = {"name"})})
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private int age;

  @Column(name = "hobby")
  private String hobby;

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  private List<Post> posts = new ArrayList<>();

  protected Member(){}

  public Member(String name, int age, String hobby) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }

  public void update(String newName, int newAge, String newHobby){
    name = newName;
    age = newAge;
    hobby = newHobby;
  }

  public Long getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public int getAge(){
    return age;
  }

  public String getHobby(){
    return hobby;
  }

  public List<Post> getPosts(){
    return posts;
  }
}
