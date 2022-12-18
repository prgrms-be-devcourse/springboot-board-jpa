package com.devcourse.springbootboardjpa.common.dto.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageDTO {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private int page;
        private int size;

        public Pageable makePageable() {
            return PageRequest.of(page, size, Sort.by("createAt"));
        }
    }

    @Getter
    public static class Response<E, DTO> {
        private List<DTO> data;
        private boolean isPrev;
        private boolean isNext;
        private List<Integer> pages;
        private int nowPage;

        public Response(Page<E> page, Function<E, DTO> entityToDtoFunction) {
            int startPageNumber = 10 * ((page.getNumber()) / 10) + 1;
            int endPageNumber = Math.min(page.getTotalPages(), startPageNumber + 9);

            this.nowPage = page.getNumber() + 1;
            this.data = page.getContent()
                    .stream().map(entityToDtoFunction)
                    .collect(Collectors.toList());
            this.isNext = startPageNumber + 8 < page.getTotalPages();
            this.isPrev = startPageNumber > 1;
            this.pages = IntStream.rangeClosed(startPageNumber, endPageNumber)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }
}
