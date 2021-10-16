package com.board.springbootboard.domain.posts.dto;


import com.board.springbootboard.domain.posts.PostsEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }


    // Converter
    // Controller -> Service -> Repository
    //                      DTO (temp)
    public PostsEntity toEntity() {
        return PostsEntity.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
