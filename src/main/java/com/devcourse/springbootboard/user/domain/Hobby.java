package com.devcourse.springbootboard.user.domain;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class Hobby {
	private String hobby;

	protected Hobby() {
	}

	public Hobby(String hobby) {
		this.hobby = hobby;
	}
}
