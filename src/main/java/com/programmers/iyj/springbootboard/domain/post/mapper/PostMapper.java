package com.programmers.iyj.springbootboard.domain.post.mapper;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    Post dtoToEntity(PostDto postDto);
    PostDto entityToDto(Post post);
}