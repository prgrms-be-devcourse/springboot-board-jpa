package com.example.board.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;

}
