package yjh.jpa.springnoticeboard.domain.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.entity.Post;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

//    @Mapping(target = "postDto.user", expression = "java(UserMapper.INSTANCE.userDtoToEntity(postDto.user))")
    Post postDtoToEntity(PostDto postDto);

//    @Mapping(target = "post.user", expression = "java(UserMapper.INSTANCE.userEntityToDto(post.user))")
    PostDto postToDto(Post post);

}
