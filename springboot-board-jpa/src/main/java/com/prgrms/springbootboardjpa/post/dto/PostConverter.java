package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.post.entity.Post;
import com.prgrms.springbootboardjpa.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class PostConverter {
    public Post convertToPost(PostDto postDto, User user){

        Post post = Post.builder()
                    .title(postDto.getTitle())
                    .content(postDto.getContent())
                    .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                    .createdBy(user.getNickName())
                    .build();
        post.setUser(user);

        return post;
    }

    public PostResponse convertToPostResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userNickName(post.getUser().getNickName())
                .build();
    }
}
