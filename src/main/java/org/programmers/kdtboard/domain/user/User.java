package org.programmers.kdtboard.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.programmers.kdtboard.domain.BaseEntity;

import lombok.Builder;

@Builder
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 10)
	private String name;

	@Column(nullable = false)
	private int age;

	@Column(nullable = false, length = 30)
	private String hobby;

	public User(Long id, String name, int age, String hobby) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	protected User() {
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
