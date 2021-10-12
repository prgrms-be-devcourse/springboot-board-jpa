package com.prgrms.dlfdyd96.board.user.converter;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.post.converter.PostConverter;
import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import com.prgrms.dlfdyd96.board.user.dto.CreateUserRequest;
import com.prgrms.dlfdyd96.board.user.dto.UserResponse;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  // dto -> entity
  public User convertUser(CreateUserRequest requestDto) {
    return User.builder()
        .name(requestDto.getName())
        .age(requestDto.getAge())
        .hobby(requestDto.getHobby())
        .build();
  }

  // entity -> dto
  public UserResponse convertUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .age(user.getAge())
        .name(user.getName())
        .hobby(user.getHobby())
        .postResponses(user.getPosts()
            .stream()
            .map(this::convertPostResponse)
            .collect(Collectors.toList())
        )
        .build();
  }

  public PostResponse convertPostResponse(Post post) {
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        // .content(post.getContent()) // TODO: content는 필요 없지 않나...? 생각해보자.
        .build();
  }
}
