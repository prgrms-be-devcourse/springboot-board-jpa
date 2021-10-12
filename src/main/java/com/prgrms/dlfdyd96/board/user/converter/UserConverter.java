package com.prgrms.dlfdyd96.board.user.converter;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.user.dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
  // dto -> entity
  public User convertUser(UserDto userDto) {
    return User.builder()
        .name(userDto.getName())
        .age(userDto.getAge())
        .hobby(userDto.getHobby())
        .build();
  }

  // entity -> dto
  public UserDto convertUserDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .age(user.getAge())
        .name(user.getName())
        .hobby(user.getHobby())
        .pagesDto(user.getPosts()
            .stream()
            .map(this::convertPostDto)
            .collect(Collectors.toList())
        )
        .build();
  }

  public PostDto convertPostDto(Post post) {
    return PostDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent()) // TODO: content는 필요 없지 않나...? 생각해보자.
        .build();
  }
}
