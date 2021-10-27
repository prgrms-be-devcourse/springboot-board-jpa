package com.assignment.bulletinboard.post.converter;

import com.assignment.bulletinboard.post.Post;
import com.assignment.bulletinboard.post.dto.PostSaveDto;
import com.assignment.bulletinboard.post.dto.PostUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertToPost(PostSaveDto postSaveDto) {
        return Post.builder()
                .id(postSaveDto.getId())
                .title(postSaveDto.getTitle())
                .content(postSaveDto.getContent())
                .build();
    }

    public Post ConvertUpdateDtoToPost(PostUpdateDto postUpdateDto) {
        return Post.builder()
                .id(postUpdateDto.getId())
                .title(postUpdateDto.getTitle())
                .content(postUpdateDto.getContent())
                .build();
    }

    public PostSaveDto convertToPostDto (Post post) {
        return PostSaveDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
