package com.devcourse.springbootboard.user.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devcourse.springbootboard.global.domain.BaseEntity;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private int age;

	@Embedded
	private Hobby hobby;

	protected User() {
	}

	@Builder
	public User(Long id, String name, int age, Hobby hobby) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	public void changeInfo(String name, int age, Hobby hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}
}
