package com.prgrms.boardjpa.core.commons.page;

import org.springframework.data.domain.PageRequest;

public class SimplePage {
	private static final int MIN_PAGE = 0;
	private static final int MIN_SIZE = 1;
	private final int size;
	private final int page; // offset -> size * page

	private SimplePage(int size, int page) {
		this.size = Math.max(size, MIN_SIZE);
		this.page = Math.max(page, MIN_PAGE);
	}

	public static SimplePage of(int size, int page) {
		return new SimplePage(size, page);
	}

	public int getSize() {
		return size;
	}

	public int getPage() {
		return page;
	}

	public static PageRequest defaultPageRequest() {
		return PageRequest.of(0, 10);
	}
}
