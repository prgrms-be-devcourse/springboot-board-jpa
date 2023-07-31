package com.programmers.heheboard.domain.user;

import java.util.ArrayList;
import java.util.List;

import com.programmers.heheboard.domain.post.Post;
import com.programmers.heheboard.global.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private int age;

	@Column
	private String hobby;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

	@Builder
	public User(String name, int age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	public void addPost(Post post) {
		post.setUser(this);
	}
}
