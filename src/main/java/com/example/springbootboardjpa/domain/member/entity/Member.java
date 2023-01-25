package com.example.springbootboardjpa.domain.member.entity;

import com.example.springbootboardjpa.domain.post.entity.Post;
import com.example.springbootboardjpa.global.entity.BaseTimeEntity;
import com.example.springbootboardjpa.global.exception.CustomException;
import com.example.springbootboardjpa.global.exception.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column
  private String name;
  @Column
  private Integer age;
  @Column
  private String hobby;

  @Builder.Default
  @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
  private List<Post> posts = new ArrayList<>();

  public Member(String name, int age, String hobby) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
  }

  public void isId(Long id) {
    if (!Objects.equals(this.id, id)) {
      throw new CustomException(ErrorCode.NOT_PERMISSION);
    }
  }
}
