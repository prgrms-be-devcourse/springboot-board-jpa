package com.programmers.board.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import lombok.Builder;
import lombok.Getter;

@Getter
@Where(clause = "deleteYn=0")
@DynamicUpdate
@AttributeOverride(name = "id", column = @Column(name = "customer_id"))
@Entity
public class Customer extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false,length = 3)
	private int age;

	@Column(nullable = false)
	private String hobby;

	@Column(nullable = false)
	private boolean deleteYn;

	@Builder
	public Customer(String name, int age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	protected Customer() {

	}

}
