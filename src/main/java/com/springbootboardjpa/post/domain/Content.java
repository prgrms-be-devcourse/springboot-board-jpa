package com.springbootboardjpa.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Content {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Lob
    private String content;

    public Content(String content) {
        this.content = content;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
