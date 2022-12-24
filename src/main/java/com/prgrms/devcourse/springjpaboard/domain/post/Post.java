package com.prgrms.devcourse.springjpaboard.domain.post;

import static com.google.common.base.Preconditions.*;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.global.common.base.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

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
	public Post(String title, String content, User user) {
		checkArgument(StringUtils.hasText(title));
		checkArgument(StringUtils.hasText(content));
		this.title = title;
		this.content = content;
		this.updateUser(user);
	}

	public void updatePost(String title, String content) {
		updateTitle(title);
		updateContent(content);
	}

	public void updateTitle(String title) {
		checkArgument(StringUtils.hasText(title));
		this.title = title;
	}

	public void updateContent(String content) {
		checkArgument(StringUtils.hasText(content));
		this.content = content;
	}

	public void updateUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Post post = (Post)o;
		return Objects.equals(getId(), post.getId()) && Objects.equals(getTitle(), post.getTitle())
			&& Objects.equals(getContent(), post.getContent()) && Objects.equals(getUser().getId(),
			post.getUser().getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getTitle(), getContent(), getUser().getId());
	}
}
