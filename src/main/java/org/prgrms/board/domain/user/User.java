package org.prgrms.board.domain.user;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.StringUtils.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.board.domain.BaseEntity;

import lombok.Getter;

@Getter
@Table(name = "user")
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false)
	private int age;

	private String hobby;

	protected User() {/*no-op*/}

	public User(String name, int age, String hobby) {
		checkArgument(isNotEmpty(name), "name must be provided.");
		checkArgument(age >= 0, "age must be greater than zero.");

		this.name = name;
		this.age = age;
		this.hobby = hobby;
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