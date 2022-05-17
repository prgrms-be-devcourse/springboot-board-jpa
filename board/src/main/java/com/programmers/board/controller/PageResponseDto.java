package com.programmers.board.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PageResponseDto<DTO, ENTITY> {

	public static final int FIX_PAGE_SIZE = 10;
	private List<DTO> dtos;
	private int total;
	private int page;
	private int size;
	private int start;
	private int end;
	private boolean previous;
	private boolean next;

	private List<Integer> pageInventories;

	public PageResponseDto(Page<ENTITY> page, Function<ENTITY, DTO> functional) {
		dtos = page.stream().map(functional).collect(Collectors.toList());
		total = page.getTotalPages();
		buildPageInventories(page.getPageable());
	}

	private void buildPageInventories(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가
		this.size = pageable.getPageSize();

		//temp end page
		int tempEnd = (int)(Math.ceil(page / (double)FIX_PAGE_SIZE)) * FIX_PAGE_SIZE;

		start = tempEnd - 9;

		previous = start > 1;

		end = Math.min(total, tempEnd);

		next = total > tempEnd;

		pageInventories = IntStream.rangeClosed(start, end).boxed().toList();
	}
}
