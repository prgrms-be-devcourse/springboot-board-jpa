package com.programmers.jpaboard.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.programmers.jpaboard.domain.BaseEntity;
import com.programmers.jpaboard.domain.post.entity.Post;
import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class User extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Size(max = 30)
	@Column(nullable = false)
	private String name;

	@NotEmpty
	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@NotNull
	@Positive
	private int age;

	@Size(max = 20)
	private String hobby;

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	List<Post> posts = new ArrayList<>();

	public User(String name, String email, int age, String hobby) {
		this.name = name;
		this.email = email;
		this.age = age;
		this.hobby = hobby;
	}

	public User(Long id, String name, String email, int age, String hobby) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.hobby = hobby;
	}

	public void addPost(Post post) {
		post.setUser(this);
	}

	public void changeUser(String email, String hobby) {
		this.email = email;
		this.hobby = hobby;
	}
}
