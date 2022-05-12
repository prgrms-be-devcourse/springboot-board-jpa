package org.prgms.board.domain.post.domain;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.prgms.board.domain.user.domain.User;

import com.google.common.base.Preconditions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
	public static final int MAX_TITLE_LENGTH = 255;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = MAX_TITLE_LENGTH, nullable = false)
	private String title;

	@Lob
	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	private LocalDateTime createdAt;

	private Post(String title, String content) {
		checkArgument(validateTitle(title), "제목은 1자 이상255자 이하이어야 합니다.");
		checkNotNull(content, "내용은 필수입니다.");

		this.title = title;
		this.content = content;
		this.createdAt = LocalDateTime.now();
	}

	private boolean validateTitle(String title) {
		return Objects.nonNull(title) && title.length() > 0 && title.length() <= MAX_TITLE_LENGTH;
	}

	public Post create(String title, String content) {
		return new Post(title, content);
	}

	// 얀관관계 편의 메서드 START

	public void setUser(User user) {
		if (Objects.nonNull(this.user)) {
			this.user.getPosts().remove(this);
		}

		this.user = user;
		user.getPosts().add(this);
	}

	// 연관관계 편의 메서드 END

}
