package com.programmers.iyj.springbootboard.domain.post.mapper;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostMapper {

    @Mapping(source = "userDto", target = "user")
    Post dtoToEntity(PostDto postDto);

    @Mapping(source = "user", target = "userDto")
    PostDto entityToDto(Post post);
}