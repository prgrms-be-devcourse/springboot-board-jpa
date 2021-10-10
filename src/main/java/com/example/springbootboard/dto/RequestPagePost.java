package com.example.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class RequestPagePost {
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;

    private int page;
    private int size;
    private String direction;
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }
    public void setSize(int size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    // getter
    public org.springframework.data.domain.PageRequest of() throws IllegalArgumentException {
        return org.springframework.data.domain.PageRequest.of(page -1, size, Sort.Direction.fromString(direction), "createdAt");
    }
}
