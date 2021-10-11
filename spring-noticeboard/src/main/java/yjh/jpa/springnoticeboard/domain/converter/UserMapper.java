package yjh.jpa.springnoticeboard.domain.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.entity.Post;
import yjh.jpa.springnoticeboard.domain.entity.User;

import java.util.Collections;
import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//    @Mapping(target = "posts", expression = "java(new ArrayList<Post>())")
    User userDtoToEntity(UserDto userDto);

//    @Mapping(target = "user.posts", expression = "java(UserMapper.INSTANCE.toDtoList(user.posts))")
    UserDto userEntityToDto(User user);

    List<Post> toDtoList(List<PostDto> posts);

    List<PostDto> toEntityList(List<Post> postDtos);

}
