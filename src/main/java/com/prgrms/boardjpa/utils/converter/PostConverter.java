package com.prgrms.boardjpa.utils.converter;

import com.prgrms.boardjpa.entity.Post;
import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  public static Post postRequestToPost(PostRequest postRequest) {
    return Post
        .builder()
        .title(postRequest.getTitle())
        .content(postRequest.getContent())
        .user(
            UserConverter.userDtoToUser(postRequest.getUserDto())
        )
        .build()
        ;
  }

  public static PostDto postToPostDto(Post post) {
    return PostDto
        .builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .userDto(
            UserConverter.userToUserDto(post.getUser())
            )
        .build();
  }
}
