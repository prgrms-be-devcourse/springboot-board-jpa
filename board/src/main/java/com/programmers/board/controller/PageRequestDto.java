package com.programmers.board.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestDto {
	@Positive(message = "양수만 가능합니다.")
	private int page;

	@Range(min = 5,max = 20,message = "해당 최소 5개 ~ 20 까지 가능합니다..")
	private int pageSize;

	public PageRequestDto(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	public Pageable getPageable(Sort sort) {
		return PageRequest.of(this.page - 1, pageSize, sort);
	}
}
