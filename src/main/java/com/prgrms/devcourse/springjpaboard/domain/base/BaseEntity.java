package com.prgrms.devcourse.springjpaboard.domain.base;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = "created_at")
	public LocalDateTime createdAt;

	@Column(name = "created_by")
	public String createdBy;
}
