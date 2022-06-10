package com.prgrms.boardjpa.application.post.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;
import org.springframework.util.Assert;

import com.prgrms.boardjpa.application.post.exception.LikeOwnPostException;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.core.commons.domain.BaseEntity;
import com.prgrms.boardjpa.core.commons.exception.CreationFailException;

@Entity
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(name = "writerName", nullable = false, updatable = false)
	private String createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id", nullable = false, updatable = false)
	private User writer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
	private List<PostLike> likes = new ArrayList<>();

	@Formula("(select count(*) from post_like pl where pl.post_id = id)")
	private int likeCount;

	protected Post() {
	}

	public Post(String title, User writer, String content) {
		this(null, title, writer, content);
	}

	private Post(PostBuilder builder) {
		this(builder.id, builder.title, builder.writer, builder.content);
	}

	private Post(Long id, String title, User writer, String content) {
		validateTitle(title);
		validateContent(content);
		validateWriter(writer);

		this.id = id;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.createdBy = writer.getName();
	}

	public static PostBuilder builder() {
		return new PostBuilder();
	}

	public Post edit(String title, String content) {
		validateTitle(title);
		validateContent(content);

		this.title = title;
		this.content = content;

		return this;
	}

	public void like(User user) {
		if (user.isSameUser(writer)) {
			throw new LikeOwnPostException(
				"자신이 작성한 게시글에는 좋아요 할 수 없습니다 : writer +" + user.getId() + " post: " + this.getId());
		}
		this.likedBy(user)
			.ifPresentOrElse(
				this::deleteLike,
				() -> this.addLike(new PostLike(this, user))
			);
	}

	private void deleteLike(PostLike like) {
		likes.remove(like);
		likeCount = likeCount > 0 ? likeCount - 1 : 0;
	}

	private void addLike(PostLike like) {
		likes.add(like);
		likeCount += 1;
	}

	private Optional<PostLike> likedBy(User user) {
		return this.likes.stream()
			.filter(like -> like.getUser().isSameUser(user))
			.findAny();
	}

	public Long getId() {
		return this.id;
	}

	public String getContent() {
		return this.content;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public String getTitle() {
		return this.title;
	}

	public int getLikeCount() {
		return this.likeCount;
	}

	public List<PostLike> getLikes() {
		return this.likes;
	}

	public static class PostBuilder {
		private Long id;
		private String title;
		private String content;
		private User writer;

		private PostBuilder() {
		}

		public PostBuilder title(String title) {
			this.title = title;

			return this;
		}

		public PostBuilder content(String content) {
			this.content = content;

			return this;
		}

		public PostBuilder writer(User user) {
			this.writer = user;

			return this;
		}

		public PostBuilder id(Long id) {
			this.id = id;

			return this;
		}

		public Post build() {
			try {
				return new Post(this);
			} catch (IllegalArgumentException e) {

				throw new CreationFailException(Post.class, e);
			}
		}
	}

	private void validateTitle(String title) {
		Assert.hasText(title, "제목은 비어있을 수 없습니다");
	}

	private void validateContent(String content) {
		Assert.hasText(content, "본문은 비어있을 수 없습니다");
	}

	private void validateWriter(User writer) {
		Assert.notNull(writer, "작성자가 null 일 수 없습니다");
	}
}
