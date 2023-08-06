package com.programmers.board.dto.service;

import com.programmers.board.dto.request.UsersGetRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersGetCommand {
    private final int page;
    private final int size;

    public static UsersGetCommand from(UsersGetRequest request) {
        return new UsersGetCommand(
                request.getPage(),
                request.getSize()
        );
    }

    public static UsersGetCommand of(int page, int size) {
        return new UsersGetCommand(page, size);
    }

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
