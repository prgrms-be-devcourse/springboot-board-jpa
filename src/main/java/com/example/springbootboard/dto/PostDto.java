package com.example.springbootboard.dto;

import com.example.springbootboard.domain.User;

public record PostDto(Long id, String title, String content, User user) {
}
