package com.kdt.jpa.domain.member.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
	private String name;
	@Column(name = "age")
	private int age;
	@Column(name = "hobby")
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
