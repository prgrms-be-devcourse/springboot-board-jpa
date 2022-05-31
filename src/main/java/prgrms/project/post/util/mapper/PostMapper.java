package prgrms.project.post.util.mapper;

import org.mapstruct.Mapper;
import prgrms.project.post.domain.post.Post;
import prgrms.project.post.service.post.PostDto;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDto, Post> {
}
