package org.prgrms.kdt.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.domain.Post;
import org.prgrms.kdt.domain.User;
import org.prgrms.kdt.dto.PostDto;
import org.prgrms.kdt.dto.PostDto.PostResponse;

@Mapper(componentModel = "spring")
public interface PostMapper {

  Post of(PostDto.PostRequest postRequest, User user);

  PostResponse of(Post post);
}