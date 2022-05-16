package org.programmers.kdtboard.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.programmers.kdtboard.domain.BaseEntity;

@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 10)
	private String name;
	@Column(nullable = false)
	private int age;
	private String hobby;

	private User(String name, int age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	private User(Long id, String name, int age, String hobby, LocalDateTime createdAt, String createdBy) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.hobby = hobby;
		this.setCreatedAt(createdAt);
		this.setCreatedBy(createdBy);
	}

	protected User() {

	}

	public static User create(String name, int age, String hobby) {
		return new User(name, age, hobby);
	}

	public static User create(Long id, String name, int age, String hobby, LocalDateTime createdAt, String createdBy) {
		return new User(id, name, age, hobby, createdAt, createdBy);
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
}
