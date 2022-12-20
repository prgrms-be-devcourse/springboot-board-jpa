package com.sdardew.board.service;

import com.sdardew.board.domain.post.Post;
import com.sdardew.board.domain.user.User;
import com.sdardew.board.dto.post.DetailedPostDto;
import com.sdardew.board.dto.post.PostDto;
import com.sdardew.board.dto.user.UserDto;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

  public UserDto toUserDto(User user) {
    return new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
  }

  public DetailedPostDto toDetailedPostDto(Post post) {
    return new DetailedPostDto(post.getId(), post.getTitle(), post.getContent(), post.getCreateAt(), toUserDto(post.getUser()));
  }

  public PostDto toPostDto(Post post) {
    return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getCreateAt(), post.getUser().getId());
  }
}
