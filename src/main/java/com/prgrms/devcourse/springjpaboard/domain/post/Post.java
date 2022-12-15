package com.prgrms.devcourse.springjpaboard.domain.post;

import java.util.Objects;

import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title", nullable = false, length = 20)
	private String title;

	@Lob
	@Column(name = "content", nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Builder
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public PostResponseDto toPostResponseDto() {
		return PostResponseDto.builder()
			.id(this.id)
			.title(this.title)
			.content(this.content)
			.build();
	}

	public void updatePost(Post post) {
		this.title = post.getTitle();
		this.content = post.getContent();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Post post = (Post)o;
		return Objects.equals(getId(), post.getId()) && Objects.equals(getTitle(), post.getTitle())
			&& Objects.equals(getContent(), post.getContent()) && Objects.equals(getUser(),
			post.getUser());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getTitle(), getContent(), getUser());
	}
}
