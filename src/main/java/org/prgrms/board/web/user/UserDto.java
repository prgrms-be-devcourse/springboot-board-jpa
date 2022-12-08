package org.prgrms.board.web.user;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.board.domain.user.User;

public class UserDto {

	private final Long id;
	private final String name;
	private final int age;
	private final String hobby;
	private final LocalDateTime createdAt;
	private final String createdBy;

	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.age = user.getAge();
		this.hobby = user.getHobby();
		this.createdAt = user.getCreatedAt();
		this.createdBy = user.getCreatedBy();
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("name", name)
			.append("age", age)
			.append("hobby", hobby)
			.append("createdAt", createdAt)
			.append("createdBy", createdBy)
			.toString();
	}
}