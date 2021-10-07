package com.eden6187.jpaboard.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "name")
  String name;

  @Column(name = "age")
  int age;

  @Column(name = "hobby", length = 30)
  String hobby;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  List<Post> posts = new ArrayList<>();
}
