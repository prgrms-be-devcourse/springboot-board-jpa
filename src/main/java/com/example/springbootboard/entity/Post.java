package com.example.springbootboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
// @Builder
public class Post extends BaseEntity{
    @Id
    private Long id;

}
