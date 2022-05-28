package org.prgrms.board.domain.post;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.StringUtils.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.board.domain.BaseEntity;
import org.prgrms.board.domain.user.User;

@Table(name = "post")
@Entity
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private User writer;

	 protected Post() {/*no-op*/}

	public Post(String title, String content, User writer) {
		checkArgument(isNotEmpty(title), "title must be provided.");
		checkArgument(isNotEmpty(content), "content must be provided.");
		checkArgument(writer != null, "writer must be provided.");

		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	//== 비지니스 로직 ==//
	public void modifyTitleAndContent(String title, String content) {
		checkArgument(isNotEmpty(title), "title must be provided.");
		checkArgument(isNotEmpty(content), "content must be provided.");

		this.title = title;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public User getWriter() {
		return writer;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("title", title)
			.append("content", content)
			.append("writer", writer)
			.append("createdAt", createdAt)
			.append("createdBy", createdBy)
			.toString();
	}
}
