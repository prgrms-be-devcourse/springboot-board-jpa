package com.example.jpaboard.post.service.dto;

public record SaveRequest(Long memberId,
                          String title,
                          String content) { }
