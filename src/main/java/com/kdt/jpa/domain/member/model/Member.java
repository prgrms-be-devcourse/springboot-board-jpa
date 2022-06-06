package com.kdt.jpa.domain.member.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kdt.jpa.domain.BaseEntity;
import com.kdt.jpa.domain.post.model.Post;

import lombok.Builder;

@Entity
@Table(name = "member")
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	@Size(min = 2, max = 25)
	@NotNull
	@NotBlank
	private String name;

	@Column(name = "age")
	@NotNull
	@Min(1)
	private int age;

	@Column(name = "hobby")
	@Size(max = 16)
	private String hobby;

	@OneToMany(mappedBy = "author")
	private List<Post> posts = new ArrayList<>();

	protected Member() {
	}

	@Builder
	public Member(Long id, String name, int age, String hobby) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	private Member(String name, int age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
		setCreatedAt(LocalDateTime.now());
		setCreatedBy(name);
	}

	public static Member generateNewMemberInstance(String name, int age, String hobby) {
		return new Member(name, age, hobby);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getHobby() {
		return hobby;
	}

	public List<Post> getPosts() {
		return posts;
	}
}
