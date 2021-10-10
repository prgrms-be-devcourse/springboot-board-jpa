package com.prgrms.dlfdyd96.board.post.converter;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  // dto -> entity
  public Post convertPost(PostDto postDto) {

    return Post.builder()
        .title(postDto.getTitle())
        .content(postDto.getContent())
        .user(this.convertUser(postDto.getUserDto()))
        .build();
  }

  private User convertUser(UserDto userDto) {

    return User.builder()
        .id(userDto.getId())
        .name(userDto.getName())
        .age(userDto.getAge())
        .hobby(userDto.getHobby())
        .build();
  }

  public PostDto convertPostDto(Post post) {
    return PostDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .userDto(this.convertUserDto(post.getUser()))
        .build();
  }

  private UserDto convertUserDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .age(user.getAge())
        .hobby(user.getHobby())
        .build();
  }
}