package com.example.springbootboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
// @Builder
public class Post extends BaseEntity{
    @Id
    private Long id;
    private String title;
    @Lob
    private String content;
}
